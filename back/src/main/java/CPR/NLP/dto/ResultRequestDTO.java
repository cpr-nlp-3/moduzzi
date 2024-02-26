package CPR.NLP.dto;

import CPR.NLP.domain.Course;
import CPR.NLP.domain.Result;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultRequestDTO {

    @NotNull
    private Course course;
    private String data;
    private String sentiment;
    private String confidence;
    private float averageRating;

    public Result toEntity() {
        return Result.builder()
                .course(course)
                .data(data)
                .averageRating(averageRating)
                .build();
    }
}
