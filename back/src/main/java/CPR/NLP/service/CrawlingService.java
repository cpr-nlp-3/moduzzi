package CPR.NLP.service;

import CPR.NLP.domain.Course;
import CPR.NLP.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CrawlingService {

    private final ReviewService reviewService;
    private final CourseService courseService;

    @Scheduled(cron = "0 0 0 * * *") //반환타입이 void고, 매개변수가 없는 메소드여야 함
    public void saveReviews() {
        List<Course> courses = courseService.findAll();
        for (Course course: courses) {
            String name = course.getName();
            String professor = course.getProfessor();

            //여기서 crawling 함수 호출 -> review content list 받아옴, 차례로 course_id와 함께 save
            List<String> reviews = executeCrawlingScript(name, professor);
            reviewService.deleteCourseReview(course); //기존 해당 course의 review들 삭제

            for (String review: reviews) {
                Review newReview = Review.builder()
                        .course(course)
                        .content(review)
                        .build();

                reviewService.save(newReview);
            }
        }
    }

    public List<String> executeCrawlingScript(String name, String professor) { //그냥 jsoup 크롤러 사용?(동적인 웹페이지에 관해서는 selenium 사용)
        List<String> reviewList = Collections.emptyList();

        try {
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("graal.js");

            // JavaScript 파일 로드
            String scriptPath = "C:/Users/dlthd/Desktop/웹_프로젝트/NLP-3/front/crawler.js";
            engine.eval(new java.io.FileReader(scriptPath));

            // JavaScript 함수 호출
            Invocable invocable = (Invocable) engine;
            Object reviews = invocable.invokeFunction("crawler", name, professor);

            if (reviews instanceof List) {
                reviewList = (List<String>) reviews;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reviewList;
    }
}
