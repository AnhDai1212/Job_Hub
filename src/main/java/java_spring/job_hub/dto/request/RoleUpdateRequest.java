package java_spring.job_hub.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleUpdateRequest {
    String name;
    String description;
    Set<String> permissions;
}
