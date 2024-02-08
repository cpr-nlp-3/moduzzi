package CPR.NLP.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    private int courseId;
    private String code;
    private String name;
    private String professor;
    private String location;
    private String time;
}

//course db 수정: 별점도 추가