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
public class JobRequest {
    String title;
    String descriptions;
    String jobType;
    LocalDate deadline;
    String location;
    //    LocalDate createAt;
    //    String status;
    List<String> jobTags;
    List<String> jobCategories;
    Double minSalary;
    Double maxSalary;
}
