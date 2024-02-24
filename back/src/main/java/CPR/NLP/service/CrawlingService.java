package CPR.NLP.service;

import CPR.NLP.domain.Course;
import CPR.NLP.domain.Review;
import CPR.NLP.repository.CourseRepository;
import CPR.NLP.repository.IntermediateRepository;
import CPR.NLP.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.*;
import org.springframework.beans.factory.annotation.Value;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
public class CrawlingService {

    private final ReviewRepository reviewRepository;
    private final IntermediateRepository intermediateRepository;
    private final CourseRepository courseRepository;

    private Set<Cookie> savedCookies;
    @Value("${everytime.id}")
    private String everytimeId;
    @Value("${everytime.password}")
    private String everytimePassword;

    @Value("${client_id}")
    private String clientId;
    @Value("${client_secret}")
    private String clientSecret;

    public String summarizeReview(String reviewContent) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-NCP-APIGW-API-KEY-ID", clientId);
        headers.set("X-NCP-APIGW-API-KEY", clientSecret);
        String summarizeUrl = "https://naveropenapi.apigw.ntruss.com/text-summary/v1/summarize";

        // 요약할 문서와 옵션 설정
        String requestBody = "{\"document\":{\"content\":\"" + reviewContent + "\"}," +
                "\"option\":{\"language\":\"ko\",\"model\":\"general\",\"tone\":3,\"summaryCount\":5}}";

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(summarizeUrl, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            String summarizedText =  response.getBody().split(":")[1].trim().replaceAll("[.,!?]", "");
            return summarizedText;
        } else {
            return "Error occurred: " + response.getStatusCode();
        }
    }

    @Scheduled(cron = "0 0 0 * * *") //반환타입이 void고, 매개변수가 없는 메소드여야 함
    public void saveReviews() {
        List<Course> courses = courseRepository.findAll();
        WebDriver driver = new ChromeDriver();

        for (Course course : courses) {
            int courseId = course.getCourseId();
            String name = course.getName();
            String professor = course.getProfessor();

            List<Map<String, Object>> reviews = executeCrawlingScript(driver, name, professor); //crawling 함수 호출 ->  rating과 content가 담긴 reviews list 받아옴, 차례로 course_id와 함께 save
            System.out.println("reviews = " + reviews);
            intermediateRepository.deleteByReviewCourseCourseId(courseId); //기존 해당 course의 intermediate 삭제
            reviewRepository.deleteByCourseCourseId(courseId); //기존 해당 course의 review들 삭제

            for (Map<String, Object> review: reviews) {
                Review newReview = Review.builder()
                        .course(course)
                        .content((String) review.get("content"))
                        .rating((int) review.get("rating"))
                        .build();

                reviewRepository.save(newReview);
            }
        }
        driver.quit(); //quit 하면 cookie 정보가 모두 사라짐
    }

    public List<Map<String, Object>> executeCrawlingScript(WebDriver driver, String name, String professor) { //동적인 웹페이지 -> selenium 사용

        // Open the webpage
        driver.get("https://everytime.kr/lecture");

        if (savedCookies != null && !savedCookies.isEmpty()) {
            for (Cookie cookie : savedCookies) {
                driver.manage().addCookie(cookie);
            }
        } else {
            login(driver);
            savedCookies = driver.manage().getCookies();
        }

        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        // Search for lecture
        List<Map<String, Object>> reviews = new ArrayList<>();
        driver.findElement(By.cssSelector("body > div > div > div.side > div > form > input[type=search]:nth-child(1)")).sendKeys(name);
        driver.findElement(By.cssSelector("body > div > div > div.side > div > form > input.submit")).click();

        // Find the professor's lecture element
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        WebElement lectureElement = null;

        try {
            lectureElement = driver.findElement(By.xpath("//div[@class='lectures']//a[@class='lecture']/div[@class='professor' and contains(text(), '" + professor + "')]"));
        } catch (Exception e) {
            System.out.println("Professor's lecture not found.");
            return reviews;
        }
        // Click on the lecture element
        lectureElement.click();
        
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        WebElement moreElement = null;
        
        try {
            moreElement = driver.findElement(By.cssSelector("body > div > div > div.pane > div > section.review > div.articles > a"));
        } catch (Exception e) {
            System.out.println("No reviews found for the professor's lecture.");
            return reviews;
        }
        moreElement.click(); //더보기 메뉴

        // Retrieve and print the reviews
        List<WebElement> starElements = driver.findElements(By.cssSelector("body > div > div > div.pane > div > div.articles > div.article > div.article_header > div.title > div.rate > span.star > span.on"));
        List<WebElement> reviewElements = driver.findElements(By.cssSelector("body > div > div > div.pane > div > div.articles > div.article > div.text"));

        for (int i = 0; i < reviewElements.size(); i++) {
            Map<String, Object> evaluate = new HashMap<>();
            String width = starElements.get(i).getAttribute("style").split("%")[0].split(":")[1].trim();
            evaluate.put("rating",  Integer.parseInt(width)/20);
            evaluate.put("content", reviewElements.get(i).getText());
            reviews.add(evaluate);
        }

        return reviews;
    }

    private void login(WebDriver driver) {
        driver.findElement(By.cssSelector("body > div:nth-child(2) > div > form > div.input > input[type=text]:nth-child(1)")).sendKeys(everytimeId);
        driver.findElement(By.cssSelector("body > div:nth-child(2) > div > form > div.input > input[type=password]:nth-child(2)")).sendKeys(everytimePassword);
        driver.findElement(By.cssSelector("body > div:nth-child(2) > div > form > input[type=submit]")).click();
    }
}
