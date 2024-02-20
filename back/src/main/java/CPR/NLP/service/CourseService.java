package CPR.NLP.service;

import CPR.NLP.domain.Course;
import CPR.NLP.dto.CourseRequestDTO;
import CPR.NLP.dto.CourseResponseDTO;
import CPR.NLP.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;

    public void saveOrUpdateCourse(CourseRequestDTO courseDTO) {
        Optional<Course> existingCourse = courseRepository.findByNameAndProfessor(courseDTO.getName(), courseDTO.getProfessor());

        if (existingCourse.isEmpty()) {
            courseRepository.save(courseDTO.toEntity());
        } else { //어차피 과목정보에 올라오는 과목들은 거의 다 변경된 과목들이므로 따로 수정됐는지 여부는 확인하지 않아도 될 듯: 그런데 같은 교수님이 같은 과목을 두 반 이상 강의하는 경우에 location과 time 어떻게 저장할지 고려해야 할듯
            Course existing = existingCourse.get();
            Course updatedCourse = Course.builder()
                    .courseId(existing.getCourseId())
                    .code(courseDTO.getCode())
                    .name(courseDTO.getName())
                    .professor(courseDTO.getProfessor())
                    .location(courseDTO.getLocation())
                    .time(courseDTO.getTime())
                    .createdAt(existing.getCreatedAt())
                    .updatedAt(LocalDateTime.now())
                    .build();

            courseRepository.save(updatedCourse);
        }
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