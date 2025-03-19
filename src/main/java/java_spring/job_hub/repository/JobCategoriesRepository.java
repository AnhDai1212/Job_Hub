package java_spring.job_hub.repository;

import java_spring.job_hub.entity.JobCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobCategoriesRepository extends JpaRepository<JobCategories, Integer> {

    Optional<JobCategories> findByCategoryName(String categoriesName);
}
