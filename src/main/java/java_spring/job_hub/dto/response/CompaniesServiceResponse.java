package java_spring.job_hub.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompaniesServiceResponse {
    Integer serviceId;
    String serviceName;
    String description;
}
