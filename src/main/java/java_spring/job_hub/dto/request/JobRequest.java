package java_spring.job_hub.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobRequest {
    String title;
    String descriptions;
    String jobType;
    LocalDate deadline;
    String location;
//    LocalDate createAt;
    String status;
    Double minSalary;
    Double maxSalary;

}
