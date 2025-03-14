package java_spring.job_hub.repository;

import java_spring.job_hub.entity.Applications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationsRepository extends JpaRepository<Applications, Integer> {

    @Query("SELECT COUNT(a) FROM Applications a WHERE a.jobs.jobId = :jobId")
    int countApplicationsByJobId(@Param("jobId") Integer jobId);
}
