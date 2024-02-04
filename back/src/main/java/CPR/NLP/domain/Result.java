package CPR.NLP.domain;

import jakarta.persistence.*;
import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    private Long result_id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
    private String data;


}
