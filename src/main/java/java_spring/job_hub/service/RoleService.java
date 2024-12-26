package java_spring.job_hub.service;

import java_spring.job_hub.dto.request.RoleCreateRequest;
import java_spring.job_hub.dto.response.ApiResponse;
import java_spring.job_hub.dto.response.RoleResponse;
import java_spring.job_hub.entity.Role;
import java_spring.job_hub.enums.Roles;
import java_spring.job_hub.exception.AppException;
import java_spring.job_hub.exception.ErrorCode;
import java_spring.job_hub.mapper.RoleMapper;
import java_spring.job_hub.repository.PermissionRepository;
import java_spring.job_hub.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService{
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;

    public RoleResponse createRole(RoleCreateRequest request){
        var role = roleMapper.toRole(request);
        if(role == null) {
            throw new AppException(ErrorCode.ROLE_NOT_EXIST);
        }

        var permissions = permissionRepository.findAllById(request.getPermissions());
        log.info("Permissions from request: {}", permissions);

//        if(permissions.isEmpty()){
//            throw new AppException(ErrorCode.PERMISSION_NOT_EXIST);
//        }

        role.setPermissions(new HashSet<>(permissions));
        roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }


    public List<RoleResponse> getAllRoles(){
        var roles = roleRepository.findAll();
        if(roles.isEmpty()) {
            throw new AppException(ErrorCode.ROLE_NOT_EXIST);
        }
        return roleMapper.toRoleListResponse(roles);
    }
    // mot cach khac hay hon
    public List<RoleResponse> getAll2() {
        var roles = roleRepository.findAll();
        return roles.stream()
                        .map(roleMapper::toRoleResponse)
                        .toList()
               ;
    }

    public void deleteRole(String roleName) {
        Role role = roleRepository.findByName(roleName).orElseThrow(
                ()-> new AppException(ErrorCode.ROLE_NOT_EXIST)
        );
        roleRepository.delete(role);
    }
}
