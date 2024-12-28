package java_spring.job_hub.dto.request;

import jakarta.validation.constraints.Size;
import java_spring.job_hub.validator.DobConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    LocalDate createAt;
}
