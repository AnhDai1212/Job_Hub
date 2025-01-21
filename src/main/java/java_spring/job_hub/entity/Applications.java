package java_spring.job_hub.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Applications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer applicationId;

    Date applicationAt;
    String status;

    @ManyToOne
    @JoinColumn(name = "jobId")
    private Jobs jobs;

    @OneToMany(mappedBy = "applications", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Application_history> applicationHistoryList;

    @ManyToOne
    @JoinColumn(name = "userId")
    User user;
}
