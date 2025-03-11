package java_spring.job_hub.entity;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfiles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // avatar thi luu boolean o cot image
    Integer profileId;

    String address;
    String phone;
    String bio;

    @ManyToOne
    @JoinColumn(name = "userId")
    User user;
}
