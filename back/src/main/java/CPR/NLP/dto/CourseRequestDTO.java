package CPR.NLP.dto;

import CPR.NLP.domain.Course;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

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

    public Course toEntity() {
        return Course.builder()
                .code(code)
                .name(name)
                .professor(professor)
                .location(location)
                .time(time)
                .build();
    }
}
