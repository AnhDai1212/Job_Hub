package java_spring.job_hub.service;

import java_spring.job_hub.dto.request.UserCreationRequest;
import java_spring.job_hub.dto.request.UserUpdateRequest;
import java_spring.job_hub.dto.response.ApiResponse;
import java_spring.job_hub.dto.response.UserResponse;
import java_spring.job_hub.entity.Role;
import java_spring.job_hub.entity.User;
import java_spring.job_hub.exception.AppException;
import java_spring.job_hub.exception.ErrorCode;
import java_spring.job_hub.mapper.UserMapper;
import java_spring.job_hub.repository.RoleRepository;
import java_spring.job_hub.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.IMessage;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService  {

    UserRepository userReponsetory;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;



    public UserResponse createUser(UserCreationRequest request) {
        log.info("Service : Create User");
        if(userReponsetory.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_ALREADY_EXIST);
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreateAt(LocalDateTime.now());
        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXIST));

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
//

        return  userMapper.toUserResponse(userReponsetory.save(user));
    }

    public UserResponse updateUser(String id,UserUpdateRequest request) {
        User user = userReponsetory.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        userMapper.updateUser(user, request);
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
//            System.out.println("Mật khẩu mới: " + request.getPassword()); // Kiểm tra giá trị mật khẩu
            user.setPassword(passwordEncoder.encode(request.getPassword()));
//            System.out.println("Mật khẩu mới sau ma hoa: " + user.getPassword()); // Kiểm tra giá trị mật khẩu
        }
//        Cap nhat user ko truyen role vao`
//        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
//            var roles = roleRepository.findAllById(request.getRoles());
//            user.setRoles(new HashSet<>(roles));
//        }
//        if (request.getRoles() == null || request.getRoles().isEmpty()) {
//            throw new IllegalArgumentException("Roles must not be null or empty" + user.getRoles());
//        }

        System.out.println("User before save: " + user.getId());
        return userMapper.toUserResponse(userReponsetory.save(user));
    }


    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUser(String id) {
//        if(S)
        return userMapper.toUserResponse(userReponsetory.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        ));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        return userReponsetory.findAll().stream().map(userMapper::toUserResponse).toList();
    }


    public void deleteUser(String id){
        User user = userReponsetory.findById(id).orElseThrow(
                () -> new RuntimeException(ErrorCode.USER_NOT_EXISTED.getMessage())
        );
        userReponsetory.deleteById(id);
    }


    public UserResponse updateRole(String id, List<String> roleNames) {
        User user = userReponsetory.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        System.out.println("User found: " + user);  // Kiểm tra xem user có tìm được không

        List<Role> roles = roleRepository.findAllById(roleNames);
        System.out.println("Found roles: " + roles);

        if (roles.isEmpty()) {
            throw new AppException(ErrorCode.ROLE_NOT_EXIST);
        }

        user.setRoles(new HashSet<>(roles));
        System.out.println("Updated user roles: " + user.getRoles());

        User updatedUser = userReponsetory.save(user);
        System.out.println("Updated user in DB: " + updatedUser);  // Kiểm tra thông tin sau khi lưu

        return userMapper.toUserResponse(updatedUser);
    }





}
