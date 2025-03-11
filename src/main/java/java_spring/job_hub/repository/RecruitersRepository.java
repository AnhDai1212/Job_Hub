package java_spring.job_hub.repository;

import java_spring.job_hub.entity.Recruiters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitersRepository extends JpaRepository<Recruiters, Integer> {}
