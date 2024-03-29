package CPR.NLP.dto;

import CPR.NLP.domain.Course;
import CPR.NLP.domain.Result;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.time.LocalDateTime;

@Data
public class ResultResponseDTO {

    @NotNull
    private int resultId;
    @NotNull
    private int courseId;
    private String data;
    private String sentiment;
    private String confidence;
    private float averageRating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ResultResponseDTO(Result result) {
        this.resultId = result.getResultId();
        this.courseId = result.getCourse().getCourseId();
        this.data = result.getData();
        this.averageRating = result.getAverageRating();
        this.createdAt = this.getCreatedAt();
        this.updatedAt = this.getUpdatedAt();
    }
}
