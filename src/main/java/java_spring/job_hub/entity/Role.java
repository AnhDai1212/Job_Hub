package java_spring.job_hub.entity;

import java.util.Set;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
