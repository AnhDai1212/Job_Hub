package java_spring.job_hub.dto.response;

import jakarta.persistence.*;
import java_spring.job_hub.entity.Companies;
import java_spring.job_hub.entity.Role;
import java_spring.job_hub.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecruitersResponse {
    private Integer id;
    private String userId;
    private String roleName;
    private Integer companyId; // Có thể null nếu không gán công ty
}