package java_spring.job_hub.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    String username;
//    String password;
    String email;
    String firstName;
    String lastName;
    String location;
    Date dob;
    Date createAt;
    Set<RoleResponse> roles;
}
