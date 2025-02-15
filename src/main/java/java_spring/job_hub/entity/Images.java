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
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer imageId;

    String imageName;
    String imageData;
    Date uploadedAt;
    Boolean isProfileImage;

    //    userId
    //    companyId
    @ManyToOne
    @JoinColumn(name = "companyId")
    Companies companies;

    @ManyToOne
    @JoinColumn(name = "userId")
    User user;
}
