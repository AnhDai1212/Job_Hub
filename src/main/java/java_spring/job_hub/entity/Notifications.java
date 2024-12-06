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
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer notificationId;

    String message;
    boolean isRead;
    Date createAt;

    @ManyToOne
    @JoinColumn(name = "userId")
    User user;

}
