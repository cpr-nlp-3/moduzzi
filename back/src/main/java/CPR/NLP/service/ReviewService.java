package CPR.NLP.service;

import CPR.NLP.domain.Course;
import CPR.NLP.dto.ReviewRequestDTO;
import CPR.NLP.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public void save(ReviewRequestDTO reviewDTO) {
        reviewRepository.save(reviewDTO.toEntity());
    }

    public void deleteCourseReview(int courseId) {
        reviewRepository.deleteByCourseId(courseId);
    }

}
