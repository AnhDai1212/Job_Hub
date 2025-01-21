package java_spring.job_hub.repository;

import java_spring.job_hub.entity.InvalidatedToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {}
