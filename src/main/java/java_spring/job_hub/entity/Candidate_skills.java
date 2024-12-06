package java_spring.job_hub.entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Candidate_skills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer skillId;
    String proficiencyLevel;

    @ManyToOne
    @JoinColumn(name = "userId")
    User user;
}
