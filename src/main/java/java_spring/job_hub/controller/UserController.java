package java_spring.job_hub.controller;

import java.util.List;
import java.util.Map;
import java_spring.job_hub.dto.request.UserCreationRequest;
import java_spring.job_hub.dto.request.UserUpdateRequest;
import java_spring.job_hub.dto.response.ApiResponse;
import java_spring.job_hub.dto.response.UserResponse;
import java_spring.job_hub.entity.User;
import java_spring.job_hub.repository.RoleRepository;
import java_spring.job_hub.repository.UserRepository;
import java_spring.job_hub.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
@AllArgsConstructor
public class UserController {
    private final RoleRepository roleRepository;
    private UserRepository userReponsetory;
    private UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        log.info("Controller : Create User");
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .result(userService.createUser(request))
                .build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .result(userService.updateUser(userId, request))
                .build();
    }

    @PutMapping("/{id}/roles")
    public ApiResponse<UserResponse> updateUserRoles(
            @PathVariable String id, @RequestBody Map<String, List<String>> roleNames) {
        System.out.println("Received API call for user ID: " + id + " with roleNames: " + roleNames.get("roleNames"));
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateRole(id, roleNames.get("roleNames")))
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId) {
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .result(userService.getUser(userId))
                .message("Get user success")
                .build();
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUsers())
                .code(1000)
                .build();
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse deleteUser(@PathVariable("userId") String userId) {
        User user = userReponsetory.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));
        userService.deleteUser(userId);
        return ApiResponse.builder()
                .code(1000)
                .message("Delete User success: " + user.getUsername())
                .build();
    }

    //    @GetMapping("/{userId}")
    //    public ApiResponse<List<RoleResponse>> getRole(@PathVariable String userId) {
    //        return
    //    }

}
