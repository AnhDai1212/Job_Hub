package java_spring.job_hub.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Jobs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    // fk
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST, CascadeType.MERGE,
                CascadeType.DETACH, CascadeType.REFRESH
            })
    @JoinTable(name = "job_tag_mapping")
    Set<JobTag> jobTags;

    @ManyToMany
    @JoinTable(name = "job_category_mapping")
    Set<JobCategories> jobCategories;

    @OneToMany(mappedBy = "jobs", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Applications> applicationsList;

    @ManyToOne
    @JoinColumn(name = "companyId")
    Companies companies;

    @ManyToOne
    @JoinColumn(name = "recruiterId")
    Recruiters recruiters;
}
