package java_spring.job_hub.repository;

import java.util.Optional;
import java_spring.job_hub.entity.Permission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
    Optional<Permission> findAllByName(String permissionName);
}
