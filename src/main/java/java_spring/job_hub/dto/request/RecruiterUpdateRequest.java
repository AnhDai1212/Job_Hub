package java_spring.job_hub.dto.request;

import java_spring.job_hub.enums.RecruiterStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecruiterUpdateRequest {
    private Integer recruiterId;
    private RecruiterStatus status; // Chỉ nhận các giá trị ENUM hợp lệ
    private Integer companyId; // Optional: Gán công ty cho HR
}
