package java_spring.job_hub.repository;

import java_spring.job_hub.entity.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorites, Integer> {

    @Query("SELECT COUNT(f) FROM Favorites f WHERE f.jobs.jobId = :jobId")
    int countFavoritesByJobId(@Param("jobId") Integer jobId);
}
