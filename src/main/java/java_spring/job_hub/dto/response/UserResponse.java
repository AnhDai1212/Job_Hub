package java_spring.job_hub.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    String id;
    String username;
    //    String password;
    String email;
    String firstName;
    String lastName;
    String location;
    LocalDate dob;
    String gender;
    String phone;
    LocalDateTime createAt;
    Set<RoleResponse> roles;
    Boolean isActivation;
}
