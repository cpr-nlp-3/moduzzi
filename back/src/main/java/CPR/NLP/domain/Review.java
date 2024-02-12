package CPR.NLP.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class) //Date 자동생성 위함
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    @Column(name="review_id")
    private Long reviewId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
    @Column(columnDefinition = "Text")
    private String content;
    private int rating;
    @CreatedDate
    @Column(name = "saved_at")
    private LocalDateTime savedAt;
}
