package java_spring.job_hub.dto.request;

import java_spring.job_hub.entity.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompaniesRequest {
    String companyName;
    String location;
    String webSite;
    //    String status;
    //    Date createAt;
    //    List<Integer> jobsList;
    //    List<Integer> companyServicesList;
    //    List<Integer> imagesList;
    //    List<Integer> recruitersList;
}
