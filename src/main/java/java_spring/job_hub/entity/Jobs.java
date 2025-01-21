package java_spring.job_hub.entity;

import java.util.Date;
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
    String location;
    Date createAt;
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
    Set<Job_tags> jobTags;

    @ManyToMany
    @JoinTable(name = "job_category_mapping")
    Set<Job_categories> jobCategories;

    @OneToMany(mappedBy = "jobs", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Applications> applicationsList;

    @ManyToOne
    @JoinColumn(name = "companyId")
    Companies companies;
}
