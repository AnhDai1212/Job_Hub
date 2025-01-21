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
public class Company_services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer serviceId;

    String serviceName;
    String description;

    @ManyToOne
    @JoinColumn(name = "companyId")
    Companies companies;
}
