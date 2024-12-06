package java_spring.job_hub.service;

import java_spring.job_hub.dto.request.UserCreationRequest;
import java_spring.job_hub.dto.request.UserUpdateRequest;
import java_spring.job_hub.dto.response.UserResponse;
import java_spring.job_hub.entity.Role;
import java_spring.job_hub.entity.User;
import java_spring.job_hub.enums.Roles;
import java_spring.job_hub.mapper.UserMapper;
import java_spring.job_hub.repository.RoleRepository;
import java_spring.job_hub.repository.UserReponsetory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService  {

    UserReponsetory userReponsetory;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;


    public UserResponse createUser(UserCreationRequest request) {
        if(userReponsetory.existsByUsername(request.getUsername())){
            throw new RuntimeException("User Exits");
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role 'USER' not found!"));

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        return  userMapper.toUserResponse(userReponsetory.save(user));
    }

    public UserResponse updateUser(String id,UserUpdateRequest request) {
        User user = userReponsetory.findById(id).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        userMapper.updateUser(user, request);
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
//            System.out.println("Mật khẩu mới: " + request.getPassword()); // Kiểm tra giá trị mật khẩu
            user.setPassword(passwordEncoder.encode(request.getPassword()));
//            System.out.println("Mật khẩu mới sau ma hoa: " + user.getPassword()); // Kiểm tra giá trị mật khẩu
        }


        if (request.getRoles() == null || request.getRoles().isEmpty()) {
            throw new IllegalArgumentException("Roles must not be null or empty" + user.getRoles());
        }

        var role = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(role));
        System.out.println("User before save: " + user);
        return userMapper.toUserResponse(userReponsetory.save(user));
    }

    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(userReponsetory.findById(id).orElseThrow(
                () -> new RuntimeException("User not found")
        ));
    }

    public List<UserResponse> getAllUsers() {
        return userReponsetory.findAll().stream().map(userMapper::toUserResponse).toList();
    }


    public void deleteUser(String id){
        User user = userReponsetory.findById(id).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        userReponsetory.deleteById(id);
    }


}
