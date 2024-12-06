package java_spring.job_hub.entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer reviewId;
    Byte rating;
    Date reviewDate;
    String reviewText;
//    fk companyID
    @ManyToOne
    @JoinColumn(name = "companyId")
    Companies companies;
//    fk userId
}
