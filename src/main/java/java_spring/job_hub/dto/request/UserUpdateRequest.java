package java_spring.job_hub.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Size(min = 8)
    String password;
    String email;
    String firstName;
    String lastName;
    String location;
    Date dob;
    Date createAt;
    List<String> roles;
}

