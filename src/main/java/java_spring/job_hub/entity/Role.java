package java_spring.job_hub.entity;

import jakarta.persistence.*;
import java_spring.job_hub.enums.Roles;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Role {
    @Id
//    @Enumerated(EnumType.STRING)
    String name;
    String description;

    @ManyToMany(fetch = FetchType.LAZY)
    Set<Permission> permissions;

}
