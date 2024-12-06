package java_spring.job_hub.repository;

import java_spring.job_hub.entity.Role;
import java_spring.job_hub.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(String roleName);



}
