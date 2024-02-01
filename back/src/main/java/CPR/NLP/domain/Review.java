package CPR.NLP.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter //dto 적용 시 setter 생략하기
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    private Long review_id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
    @Column(columnDefinition = "Text")
    private String content;

}
