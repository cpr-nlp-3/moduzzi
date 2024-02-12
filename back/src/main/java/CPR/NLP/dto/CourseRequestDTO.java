package CPR.NLP.dto;

import CPR.NLP.domain.Course;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import net.bytebuddy.asm.Advice;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Builder
public class CourseRequestDTO {

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

    public Course toEntity() {
        return Course.builder()
                .code(code)
                .name(name)
                .professor(professor)
                .location(location)
                .time(time)
                .createdAt(createdAt != null ? createdAt: LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
