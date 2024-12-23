package java_spring.job_hub.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import java_spring.job_hub.enums.Roles;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    String id;
    String username;
    String password;
    String email;
    String firstName;
    String lastName;
    String location;
    Date dob;
    Date createAt;
    @ManyToMany
    Set<Role> roles;

    @OneToOne(mappedBy = "user")
    Companies companies;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Applications> applicationsList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Images> imagesList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<User_profiles> userProfilesList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Candidate_skills> candidateSkillsList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Favorites> favoritesList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Notifications> notificationsList;

}
