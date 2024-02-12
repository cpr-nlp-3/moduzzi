package CPR.NLP.dto;

import CPR.NLP.domain.Course;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
public class CourseResponseDTO {

    @NotNull
    private int courseId;
    @NotNull
    private String code;
    @NotNull
    private String name;
    private String professor;
    private String location;
    private String time;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public CourseResponseDTO(Course course) {
        this.courseId = course.getCourseId();
        this.code = course.getCode();
        this.name = course.getName();
        this.professor = course.getProfessor();
        this.location = course.getLocation();
        this.time = course.getTime();
        this.createdAt = course.getCreatedAt();
        this.updatedAt = course.getUpdatedAt();
    }
}
