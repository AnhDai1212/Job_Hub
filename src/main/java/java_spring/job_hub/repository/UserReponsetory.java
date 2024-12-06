package java_spring.job_hub.repository;

import java_spring.job_hub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserReponsetory extends JpaRepository<User, String> {
    boolean existsByUsername(String username);

    Optional<User> findUserByUsername(String username);
}
