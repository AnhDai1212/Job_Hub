package java_spring.job_hub.repository;

import java.util.Optional;
import java_spring.job_hub.entity.JobTag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobTagRepository extends JpaRepository<JobTag, Integer> {
    Optional<JobTag> findByTagName(String name); // Thay findByName từ TagsRepository

//    boolean existsByJobTagIdAndJobs_JobId(Integer jobTagId, Integer jobId); // Kiểm tra liên kết
}
