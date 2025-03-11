package java_spring.job_hub.dto.response;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecruitersResponse {
    private String userId;
    private String roleName;
    private Integer companyId; // Có thể null nếu không gán công ty
}
