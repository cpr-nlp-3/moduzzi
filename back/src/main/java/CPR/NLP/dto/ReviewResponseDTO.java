package CPR.NLP.dto;

import CPR.NLP.domain.Course;
import CPR.NLP.domain.Review;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewResponseDTO {

    @NotNull
    private Long reviewId;
    @NotNull
    private int courseId;
    @NotNull
    private String content;
    @NotNull
    private int rating;
    private LocalDateTime savedAt;

    public ReviewResponseDTO(Review review) {
        this.reviewId = review.getReviewId();
        this.courseId = review.getCourse().getCourseId();
        this.content = review.getContent();
        this.rating = review.getRating();
        this.savedAt = review.getSavedAt();
    }
}