package java_spring.job_hub.entity;

import java.util.Date;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Application_history {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer historyId;

    String status;
    Date dateAt;

    @ManyToOne
    @JoinColumn(name = "applicationId")
    Applications applications;
}
