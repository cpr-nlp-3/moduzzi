package CPR.NLP.repository;

import CPR.NLP.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    void deleteByCourseCourseId(int courseId);
}
