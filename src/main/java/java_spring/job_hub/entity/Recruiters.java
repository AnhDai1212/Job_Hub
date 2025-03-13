package java_spring.job_hub.entity;

import java.util.List;
import java_spring.job_hub.enums.RecruiterStatus;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

    @Enumerated(EnumType.STRING)
    private RecruiterStatus status;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonBackReference
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

    @OneToMany(mappedBy = "recruiters", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Jobs> jobsList;
}
