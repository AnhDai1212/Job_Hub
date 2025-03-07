package java_spring.job_hub.dto.request;

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
public class RecruitersRequest {

    Integer id;
    String userId;
    Companies companies;
    Role role;
}
