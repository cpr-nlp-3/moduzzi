package CPR.NLP.dto;

import CPR.NLP.domain.Course;
import CPR.NLP.domain.Review;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewRequestDTO {

    @NotNull
    private Course course;
    @NotNull
    private String content;
    @NotNull
    private int rating;

    public Review toEntity() {
        return Review.builder()
                .course(course)
                .content(content)
                .rating(rating)
                .build();
    }
}
