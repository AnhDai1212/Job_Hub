package java_spring.job_hub.mapper;

import java.util.List;
import java_spring.job_hub.dto.request.PermissionRequest;
import java_spring.job_hub.dto.response.PermissionResponse;
import java_spring.job_hub.entity.Permission;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

    List<PermissionResponse> permissions(List<Permission> permissions);
}
