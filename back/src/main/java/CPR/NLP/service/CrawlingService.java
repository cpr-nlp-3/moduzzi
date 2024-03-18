package CPR.NLP.service;

import CPR.NLP.domain.Course;
import CPR.NLP.domain.Result;
import CPR.NLP.domain.Review;
import CPR.NLP.repository.CourseRepository;
import CPR.NLP.repository.ResultRepository;
import CPR.NLP.repository.ReviewRepository;
import com.google.gson.*;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
public class CrawlingService {

    private final ReviewRepository reviewRepository;
    private final CourseRepository courseRepository;
    private final ResultRepository resultRepository;
    private final PythonServiceCaller pythonServiceCaller;

    private Set<Cookie> savedCookies;
    @Value("${everytime.id}")
    private String everytimeId;
    @Value("${everytime.password}")
    private String everytimePassword;

    @Value("${client_id}")
    private String clientId;
    @Value("${client_secret}")
    private String clientSecret;


    public static String[] splitIntoSentences(String text) {
        // 각 문장을 기호(?, !, . 등) 또는 줄바꿈(\n)을 기준으로 분리
        return text.split("[?!.\\n]");
    }

    public boolean isEnoughWords(String text) {
        Boolean isEnough = false;
        String[] lines = text.split("\n");

        for (String line : lines) {
            String[] words = line.split("\\s+"); // 공백 문자로 단어를 분리
            if (words.length >= 5)
                isEnough = true;
        }
        return isEnough;
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
            float size = reviews.size();
            if (size == 0)
                size = 1;
            reviewRepository.deleteByCourseCourseId(courseId); //기존 해당 course의 review들 삭제

            String text = "";
            String data = "";
            String feeling = "";
            String allReviews = "";
            String sentiment = "";
            String confidence = "";
            float averageRating = 0;

            for (Map<String, Object> review: reviews) {
                Review newReview = Review.builder()
                        .course(course)
                        .content((String) review.get("content"))
                        .rating((int) review.get("rating"))
                        .build();

                reviewRepository.save(newReview);
                //allReviews += newReview.getContent().replace("\n", " ");
                allReviews += newReview.getContent();
                averageRating += newReview.getRating();

                if ((text.length()+newReview.getContent().length()) <= 2000){ //클로바 API: 최대 2000자
                    //text += newReview.getContent().replace("\n", " ");
                    text += newReview.getContent();
                } else {
                    String[] sentences = splitIntoSentences(newReview.getContent());
                    for (String sentence : sentences) {
                        if (text.length() + sentence.length() <= 2000) {
                            text += sentence;
                        } else {
                            data += pythonServiceCaller.callSummarizeFunction(text, clientId, clientSecret);
                            text = sentence;
                        }
                    }
                }
            }

            if (isEnoughWords(text)) { //남은 text 처리
                data += pythonServiceCaller.callSummarizeFunction(text, clientId, clientSecret);
            } else { //줄바꿈 여러개로 짧은 여러 문장으로 나뉘었을 때
                text = text.replaceAll("\n", " ");
                if (isEnoughWords(text))
                    data += pythonServiceCaller.callSummarizeFunction(text, clientId, clientSecret);
            }

            /*if (allReviews.trim() != ""){
                feeling = pythonServiceCaller.callSentimentFunction(allReviews, clientId, clientSecret);

                //Gson gson = new Gson();
                Gson gson = new GsonBuilder().setLenient().create();
                JsonElement feelingElement = gson.fromJson(feeling, JsonElement.class);

                JsonObject documentObject = gson.fromJson(feeling, JsonObject.class).get("document").getAsJsonObject();
                sentiment = documentObject.get("sentiment").getAsString();
                confidence = documentObject.get("confidence").toString();
            }*/

            int resultId = -1;
            Optional<Result> result = resultRepository.findByCourse(course);
            if (result.isPresent()) {
                resultId = result.get().getResultId();
                Result updatingResult = Result.builder()
                        .resultId(resultId)
                        .course(course)
                        .data(data)
                        .confidence(confidence)
                        .sentiment(sentiment)
                        .averageRating(averageRating/size)
                        .createdAt(result.get().getCreatedAt())
                        .build();

                resultRepository.save(updatingResult);
            } else {
                Result newResult = Result.builder()
                        .course(course)
                        .data(data)
                        .confidence(confidence)
                        .sentiment(sentiment)
                        .averageRating(averageRating/size)
                        //.createdAt(LocalDateTime.now())
                        .build();

                resultRepository.save(newResult);
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

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        // Search for lecture
        List<Map<String, Object>> reviews = new ArrayList<>();
        driver.findElement(By.cssSelector("body > div > div > div.side > div > form > input[type=search]:nth-child(1)")).sendKeys(name);
        driver.findElement(By.cssSelector("body > div > div > div.side > div > form > input.submit")).click();

        // Find the professor's lecture element
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebElement lectureElement = null;

        try {
            lectureElement = driver.findElement(By.xpath("//div[@class='lectures']//a[@class='lecture']" +
                    "[.//div[@class='professor' and contains(text(), '" + professor + "')]]" +
                    "[.//div[@class='name']/span[@class='highlight' and contains(text(), '" + name + "')]]"+
                    "[not(descendant::div[@class='name']/span[@class='highlight']/following-sibling::text()[normalize-space()])]"));
        } catch (Exception e) {
            System.out.println("Professor's lecture not found.");
            return reviews;
        }
        // Click on the lecture element
        lectureElement.click();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebElement moreElement = null;

        try {
            moreElement = driver.findElement(By.cssSelector("body > div > div > div.pane > div > section.review > div.articles > a"));
        } catch (Exception e) {
            System.out.println("No reviews found for the professor's lecture.");
            return reviews;
        }
        moreElement.click(); //더보기 메뉴*/

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
