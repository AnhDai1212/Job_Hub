package java_spring.job_hub.dto.request;

import java.time.LocalDate;
import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobUpdateRequest {
//    Integer jobId;
    String title;
    String descriptions;
    String jobType;
    LocalDate deadline;
    String location;
    List<String> jobTags;
    List<String> jobCategories;
    //    LocalDate createAt;
    //    String status;
    Double minSalary;
    Double maxSalary;
}
