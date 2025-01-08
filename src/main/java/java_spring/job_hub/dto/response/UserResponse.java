package java_spring.job_hub.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

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
    LocalDateTime createAt;
    Set<RoleResponse> roles;
}
