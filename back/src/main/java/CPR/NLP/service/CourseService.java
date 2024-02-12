package CPR.NLP.service;

import CPR.NLP.domain.Course;
import CPR.NLP.dto.CourseRequestDTO;
import CPR.NLP.dto.CourseResponseDTO;
import CPR.NLP.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;

    public void save(CourseRequestDTO courseDTO) {
        isNameAndProfessorDuplicate(courseDTO);
        courseRepository.save(courseDTO.toEntity());
    }

    public void isNameAndProfessorDuplicate(CourseRequestDTO courseDTO) {
        courseRepository.findByNameAndProfessor(courseDTO.getName(), courseDTO.getProfessor())
                .ifPresent(i -> {
                    throw new IllegalStateException("이미 존재하는 과목과 교수님 정보입니다.");
                });
        //아니면 매 학기 새로운 정보가 나오니까 예외처리는 하지 말고 이미 존재하면 save 안하도록?
    }

    public List<CourseResponseDTO> findAll(){
        List<Course> courses = courseRepository.findAll();
        if (courses.isEmpty()) {
            throw new IllegalArgumentException("아무런 과목 정보가 존재하지 않습니다.");
        }

        return courses.stream().map(CourseResponseDTO::new).collect(Collectors.toList());
    }

    public List<CourseResponseDTO> findByName(String name){
        List<Course> courses = courseRepository.findByName(name);
        if (courses.isEmpty()) {
            throw new IllegalArgumentException("해당 이름을 가지는 과목이 존재하지 않습니다.");
        }

        return courses.stream().map(CourseResponseDTO::new).collect(Collectors.toList());
    }

    public List<CourseResponseDTO> findByProfessor(String professor){
        List<Course> courses = courseRepository.findByProfessor(professor);
        if (courses.isEmpty()) {
            throw new IllegalArgumentException("해당 교수님이 강의하시는 과목이 존재하지 않습니다.");
        }

        return courses.stream().map(CourseResponseDTO::new).collect(Collectors.toList());
    }
}