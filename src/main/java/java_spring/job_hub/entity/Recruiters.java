package java_spring.job_hub.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Recruiters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonManagedReference // Quản lý phía "forward"
    User user;

    @ManyToOne
    @JoinColumn(name = "companyId")
    Companies companies;

    @ManyToOne
    @JoinColumn(
            name = "role_name",
            nullable = false,
            columnDefinition = "VARCHAR(255) COLLATE utf8mb4_general_ci" // Đồng bộ collation với bảng role
    )
    Role role;
}