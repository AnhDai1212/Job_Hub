package java_spring.job_hub.service;

import java_spring.job_hub.dto.request.PermissionRequest;
import java_spring.job_hub.dto.response.ApiResponse;
import java_spring.job_hub.dto.response.PermissionResponse;
import java_spring.job_hub.entity.Permission;
import java_spring.job_hub.exception.AppException;
import java_spring.job_hub.exception.ErrorCode;
import java_spring.job_hub.mapper.PermissionMapper;
import java_spring.job_hub.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse createPermission(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
    }
    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        return permissions.stream()
                        .map(permissionMapper :: toPermissionResponse)
                        .toList();
    }

    public void deletePermision(String permisionName){
        Permission permission = permissionRepository.findAllByName(permisionName).orElseThrow(
                () -> new AppException(ErrorCode.PERMISSION_NOT_EXIST)
        );
        permissionRepository.deleteById(permisionName);
    }
}
