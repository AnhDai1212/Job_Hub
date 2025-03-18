package java_spring.job_hub.repository;

import java.util.Optional;
import java_spring.job_hub.entity.Recruiters;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitersRepository extends JpaRepository<Recruiters, Integer> {
    Optional<Recruiters> findByUserId(String userId);

    Page<Recruiters> findAll(Pageable pageable);
}
