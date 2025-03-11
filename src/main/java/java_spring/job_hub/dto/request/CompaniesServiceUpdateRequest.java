package java_spring.job_hub.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompaniesServiceUpdateRequest {
    private String serviceName;
    private String description;
}
