package java_spring.job_hub.controller;

import java_spring.job_hub.dto.request.PermissionRequest;
import java_spring.job_hub.dto.request.RoleCreateRequest;
import java_spring.job_hub.dto.response.ApiResponse;
import java_spring.job_hub.dto.response.PermissionResponse;
import java_spring.job_hub.dto.response.RoleResponse;
import java_spring.job_hub.service.PermissionService;
import java_spring.job_hub.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.createPermission(request))
                .build();
    }

    @GetMapping()
    ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }
    @DeleteMapping("/{permission}")
    ApiResponse<Void> deleteRole(@PathVariable String permission) {
        permissionService.deletePermision(permission);
        return ApiResponse.<Void>builder()
                .message("Permission deleted successfully")
                .build();
    }
}
