package java_spring.job_hub.mapper;

import java.util.List;
import java_spring.job_hub.dto.request.RoleCreateRequest;
import java_spring.job_hub.dto.request.RoleUpdateRequest;
import java_spring.job_hub.dto.response.RoleResponse;
import java_spring.job_hub.entity.Role;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true) // tự động bỏ qua attribute permission khi mapper
    Role toRole(RoleCreateRequest request);

    RoleResponse toRoleResponse(Role role);

    List<RoleResponse> toRoleListResponse(List<Role> roles);

    @Mapping(ignore = true, target = "permissions")
    void roleUpdate(@MappingTarget Role role, RoleUpdateRequest request);
}
