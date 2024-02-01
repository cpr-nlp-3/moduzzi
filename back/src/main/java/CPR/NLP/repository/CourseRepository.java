package CPR.NLP.repository;

import CPR.NLP.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    Optional<Course> findByNameAndProfessor(String name, String professor); // 나중에 이렇게 검색했을 때 결과가 존재할 경우 crawler 호출
    Optional<Course> findByCode(String code);
    //과목코드로 검색하면 해당하는 결과가 존재하는지 찾고 존재하면 과목명과 교수님 성함 return -> findByNameAndProfessor 함수 호출
    //또는 과목코드로도 크롤링되는 함수 이용
}
