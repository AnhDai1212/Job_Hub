package java_spring.job_hub.dto.request;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String username;

    @Size(min = 8)
    String password;

    String email;
    String firstName;
    String lastName;
    String location;
    LocalDate dob;
    //    Date createAt;
    List<String> roles;
    String gender;
    String phone;
    Boolean isActivation;
}
