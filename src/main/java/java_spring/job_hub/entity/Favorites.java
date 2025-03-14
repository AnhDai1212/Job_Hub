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
public class Favorites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer favoritesId;

    Date createAt;

    @ManyToOne
    @JoinColumn(name = "userId")
    User user;

    @ManyToOne
    @JoinColumn(name = "jobId")
    Jobs jobs;
}
