package java_spring.job_hub.dto.request;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobUpdateRequest {
    Integer jobId;
    String title;
    String descriptions;
    String jobType;
    LocalDate deadline;
    String location;
    //    LocalDate createAt;
    //    String status;
    Double minSalary;
    Double maxSalary;
}
