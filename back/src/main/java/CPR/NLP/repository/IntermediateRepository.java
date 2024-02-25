package CPR.NLP.repository;

import CPR.NLP.domain.Course;
import CPR.NLP.domain.Intermediate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntermediateRepository extends JpaRepository<Intermediate, Long> {
    void deleteByCourseCourseId(int courseId);
}
