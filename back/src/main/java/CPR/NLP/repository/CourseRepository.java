package CPR.NLP.repository;

import CPR.NLP.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    Optional<Course> findByNameAndProfessor(String name, String professor);
    List<Course> findByName(String name);
    List<Course> findByProfessor(String professor);
}
