package CPR.NLP.controller;

import CPR.NLP.dto.CourseResponseDTO;
import CPR.NLP.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:3000", "https://www.moduzzi.site/" }, allowCredentials = "true")
@RequestMapping("course")
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> courseList() {
        try {
            List<CourseResponseDTO> courseDTOs = courseService.findAll();
            return ResponseEntity.ok(courseDTOs);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<CourseResponseDTO>> courseListByName(@PathVariable(name="name") String name) {
        try {
            List<CourseResponseDTO> courseDTOs = courseService.findByName(name);
            return ResponseEntity.ok(courseDTOs);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/professor/{professor}")
    public ResponseEntity<List<CourseResponseDTO>> courseListByProfessor(@PathVariable(name="professor") String professor) {
        try {
            List<CourseResponseDTO> courseDTOs = courseService.findByProfessor(professor);
            return ResponseEntity.ok(courseDTOs);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

}
