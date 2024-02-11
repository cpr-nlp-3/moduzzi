package CPR.NLP.service;

import CPR.NLP.domain.Course;
import CPR.NLP.domain.Review;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
public class CrawlingService {

    private final ReviewService reviewService;
    private final IntermediateService intermediateService;
    private final CourseService courseService;

    @Value("${everytime.id}")
    private String everytimeId;
    @Value("${everytime.password}")
    private String everytimePassword;

    @Scheduled(cron = "0 0 0 * * *") //반환타입이 void고, 매개변수가 없는 메소드여야 함
    public void saveReviews() {
        List<Course> courses = courseService.findAll();
        for (Course course: courses) {
            String name = course.getName();
            String professor = course.getProfessor();

            //여기서 crawling 함수 호출 ->  rating과 content가 담긴 reviews list 받아옴, 차례로 course_id와 함께 save
            List<Map<String, Object>> reviews = executeCrawlingScript(name, professor);
            intermediateService.deleteCourseIntermediate(course); //기존 해당 course의 intermediate 삭제
            reviewService.deleteCourseReview(course); //기존 해당 course의 review들 삭제

            for (Map<String, Object> review: reviews) {
                Review newReview = Review.builder()
                        .course(course)
                        .content((String) review.get("content"))
                        .rating((int) review.get("rating"))
                        .build();

                reviewService.save(newReview);
            }
            //System.out.println("reviews = " + reviews);
        }
    }

    public List<Map<String, Object>> executeCrawlingScript(String name, String professor) { //동적인 웹페이지에 -> selenium

        List<Map<String, Object>> reviews = new ArrayList<>();
        WebDriver driver = new ChromeDriver();

        // Open the webpage
        driver.get("https://everytime.kr/lecture");

        // Fill in ID and PW and submit
        driver.findElement(By.cssSelector("body > div:nth-child(2) > div > form > div.input > input[type=text]:nth-child(1)")).sendKeys(everytimeId);
        driver.findElement(By.cssSelector("body > div:nth-child(2) > div > form > div.input > input[type=password]:nth-child(2)")).sendKeys(everytimePassword);
        driver.findElement(By.cssSelector("body > div:nth-child(2) > div > form > input[type=submit]")).click();

        // Search for lecture
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector("body > div > div > div.side > div > form > input[type=search]:nth-child(1)")).sendKeys(name);
        driver.findElement(By.cssSelector("body > div > div > div.side > div > form > input.submit")).click();

        // Find the professor's lecture element
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        WebElement lectureElement = null;
        try {
            lectureElement = driver.findElement(By.xpath("//div[@class='lectures']//a[@class='lecture']/div[@class='professor' and contains(text(), '" + professor + "')]"));
        } catch (Exception e) {
            System.out.println("Professor's lecture not found.");
            driver.quit();
            return reviews;
        }

        // Click on the lecture element
        lectureElement.click();

        // Find the "more" element and click it
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        WebElement moreElement = null;
        try {
            moreElement = driver.findElement(By.cssSelector("body > div > div > div.pane > div > section.review > div.articles > a"));
        } catch (Exception e) {
            System.out.println("No reviews found for the professor's lecture.");
            driver.quit();
            return reviews;
        }
        moreElement.click();

        // Retrieve and print the reviews
        for (WebElement review : driver.findElements(By.cssSelector("body > div > div > div.pane > div > div.articles > div.article > div.text"))) {
            Map<String, Object> evaluate = new HashMap<>();
            int star = driver.findElements(By.cssSelector("body > div > div > div.pane > div > div.articles > div.article > div.article_header > div.title > div.rate > span.star > span.on")).size();
            evaluate.put("rating", star);
            evaluate.put("content", review.getText());
            reviews.add(evaluate);
        }

        // Close the browser
        driver.quit();
        return reviews;
    }
}
