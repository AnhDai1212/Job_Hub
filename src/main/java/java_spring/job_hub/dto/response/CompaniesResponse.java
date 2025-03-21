package java_spring.job_hub.dto.response;

import java.util.Date;
import java.util.List;
import java_spring.job_hub.entity.CompanyServiceDetail;
import java_spring.job_hub.entity.Images;
import java_spring.job_hub.entity.Jobs;
import java_spring.job_hub.entity.Recruiters;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompaniesResponse {
    String companyName;
    String location;
    String webSite;
    Date createAt;
    List<Jobs> jobsList;
    List<CompanyServiceDetail> companyServicesList;
    List<Images> imagesList;
    List<Recruiters> recruitersList;
    String avatarUrl;
    Boolean isApproved;
    String status;
}
