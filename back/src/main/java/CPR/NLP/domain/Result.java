package CPR.NLP.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class) //Date 자동생성 위함
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    @Column(name="result_id")
    private int resultId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
    private String data;
    @Column(name="average_rating")
    private float averageRating;
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updated_at;
}
