package java_spring.job_hub.dto.response;

import java.time.LocalDate;
import java.util.Set;
import java_spring.job_hub.entity.JobCategories;
import java_spring.job_hub.entity.JobTag;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobResponse {
    Integer jobId;
    String title;
    String descriptions;
    String jobType;
    LocalDate deadline;
    String location;
    LocalDate createAt;
    String status;
    Double minSalary;
    Double maxSalary;
    Set<JobTag> jobTags;
    Set<JobCategories> jobCategories;
    Integer applicationCount; // Số người ứng tuyển
    Integer favoriteCount; // Số người yêu thích
}
