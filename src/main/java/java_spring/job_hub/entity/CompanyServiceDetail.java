package java_spring.job_hub.entity;

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
public class CompanyServiceDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer serviceId;

    String serviceName;
    String description;

    @ManyToOne
    @JoinColumn(name = "companyId")
    @JsonBackReference // Bỏ qua phía "backward"
    Companies companies;
}
