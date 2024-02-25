package CPR.NLP.repository;

import CPR.NLP.domain.Course;
import CPR.NLP.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    void deleteByCourse(Course course);
    void deleteByCourseCourseId(int courseId);
    List<Review> findByCourseCourseId(int courseId);
}
