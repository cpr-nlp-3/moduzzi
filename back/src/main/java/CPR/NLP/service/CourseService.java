package CPR.NLP.service;

import CPR.NLP.domain.Course;
import CPR.NLP.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;

    public void save(Course course) {
        isNameAndProfessorDuplicate(course);
        courseRepository.save(course);
    }

    public void isNameAndProfessorDuplicate(Course course) {
        courseRepository.findByNameAndProfessor(course.getName(), course.getProfessor())
                .ifPresent(i -> {
                    throw new IllegalStateException("이미 존재하는 과목과 교수님 정보입니다.");
                });
        //아니면 매 학기 새로운 정보가 나오니까 예외처리는 하지 말고 이미 존재하면 save 안하도록?
    }

    public List<Course> findAll(){
        return courseRepository.findAll();
    }

    public List<Course> findByName(String name){
        return courseRepository.findByName(name);
    }

    public List<Course> findByProfessor(String professor){
        return courseRepository.findByProfessor(professor);
    }
}
