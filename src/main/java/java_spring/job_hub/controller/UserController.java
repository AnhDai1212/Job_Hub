package java_spring.job_hub.controller;

import jakarta.validation.Valid;
import java_spring.job_hub.dto.request.UserCreationRequest;
import java_spring.job_hub.dto.request.UserUpdateRequest;
import java_spring.job_hub.dto.response.ApiResponse;
import java_spring.job_hub.dto.response.UserResponse;
import java_spring.job_hub.entity.User;
import java_spring.job_hub.repository.UserReponsetory;
import java_spring.job_hub.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@AllArgsConstructor
public class UserController {
    private UserReponsetory userReponsetory;
    private UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .result(userService.createUser(request))
                .build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser (@PathVariable String userId, @RequestBody UserUpdateRequest request){
          return ApiResponse.<UserResponse>builder()
                  .code(1000)
                  .result(userService.updateUser(userId, request))
                  .build();
    }
    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId) {
        return  ApiResponse.<UserResponse>builder()
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

    @DeleteMapping("/{userId}")
    ApiResponse deleteUser(@PathVariable("userId") String userId) {
        User user = userReponsetory.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found!")
        );
        userService.deleteUser(userId);
        return ApiResponse.builder()
                .code(1000)
                .message("Delete User success: " + user.getUsername())
                .build();
    }

}
