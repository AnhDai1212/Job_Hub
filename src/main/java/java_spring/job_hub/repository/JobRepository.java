package java_spring.job_hub.repository;

import java_spring.job_hub.entity.Jobs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Jobs, Integer> {
    Page<Jobs> findAll(Pageable pageable);
}
