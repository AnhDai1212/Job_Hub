package java_spring.job_hub.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java_spring.job_hub.validator.DobConstraint;

import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreationRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
    String username;

    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;

    String email;
    String firstName;
    String lastName;
    String location;

    @DobConstraint(min = 16, message = "INVALID_DOB")
    LocalDate dob;

    LocalDateTime createAt;
    String gender;
    String phone;

}
