package CPR.NLP.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Course {

    @Id
    private int course_id;
    private String code;
    private String name;
    private String professor;
    private String location;
    private String time;
}
