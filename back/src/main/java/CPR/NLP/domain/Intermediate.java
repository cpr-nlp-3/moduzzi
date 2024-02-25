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
@EntityListeners(AuditingEntityListener.class)
public class Intermediate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    @Column(name="intermediate_id")
    private Long intermediateId;
    @Column(columnDefinition = "Text")
    private String material;
    private String sentiment;
    private String confidence;
    @Column(name="average_rating")
    private float averageRating;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
    @CreatedDate
    @Column(name = "saved_at")
    private LocalDateTime savedAt;
}
