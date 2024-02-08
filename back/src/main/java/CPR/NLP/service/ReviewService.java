package CPR.NLP.service;

import CPR.NLP.domain.Course;
import CPR.NLP.domain.Review;
import CPR.NLP.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public void save(Review review) {
        reviewRepository.save(review);
    }

    public void deleteCourseReview(Course course) {
        reviewRepository.deleteByCourse(course);
    }

}
