package java_spring.job_hub.repository;

import java_spring.job_hub.entity.Recruiters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecruitersRepository extends JpaRepository<Recruiters, Integer> {
     Optional<Recruiters> findByUserId(String userId);
}
