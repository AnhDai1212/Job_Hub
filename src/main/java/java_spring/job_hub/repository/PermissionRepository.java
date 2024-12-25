package java_spring.job_hub.repository;

import java_spring.job_hub.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
    Optional<Permission> findAllByPermissionName(String permissionName);
}
