package java_spring.job_hub.dto.request;

import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordCreationRequest {
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;
}
