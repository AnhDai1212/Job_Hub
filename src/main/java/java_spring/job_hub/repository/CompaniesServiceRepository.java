package java_spring.job_hub.repository;

import java_spring.job_hub.entity.CompanyServiceDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompaniesServiceRepository extends JpaRepository<CompanyServiceDetail, Integer> {}
