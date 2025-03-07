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
public class Companies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer companyId;

    String companyName;
    String location;
    String webSite;
    Date createAt;

    // fk userId
    @OneToMany(mappedBy = "companies", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Jobs> jobsList;

    @OneToMany(mappedBy = "companies", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Company_services> companyServicesList;

    @OneToMany(mappedBy = "companies", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Reviews> reviewsList;

    @OneToMany(mappedBy = "companies", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Images> imagesList;

//    @OneToOne()
//    @JoinColumn(name = "userId")
//    User user;

    @OneToMany(mappedBy = "companies", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Recruiters> recruitersList;

}
