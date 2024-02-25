package CPR.NLP.repository;

import CPR.NLP.domain.Course;
import CPR.NLP.domain.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result, Integer> {
    Optional<Result> findByCourse(Course course);
    Optional<Result> findByCourseNameAndCourseProfessor(String name, String professor);
    Optional<Result> findByCourseCourseId(Integer integer);
}
