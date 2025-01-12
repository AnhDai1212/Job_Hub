package java_spring.job_hub.service;

import java_spring.job_hub.dto.request.UserCreationRequest;
import java_spring.job_hub.dto.response.UserResponse;
import java_spring.job_hub.entity.Role;
import java_spring.job_hub.entity.User;
import java_spring.job_hub.enums.Roles;
import java_spring.job_hub.exception.AppException;
import java_spring.job_hub.mapper.UserMapper;
import java_spring.job_hub.repository.RoleRepository;
import java_spring.job_hub.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource("/test.properties")
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private UserCreationRequest request;
    private UserResponse userResponse;
    private User user;
    private LocalDate dob;
    @MockBean
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private Role role;

    @BeforeEach
    void initData(){
        dob = LocalDate.of(2003,2,12);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime createAt = LocalDateTime.parse("2025-01-05T00:40:30.8965341", formatter);
        request = UserCreationRequest.builder()
                .username("abc61")
                .firstName("John")
                .lastName("Doe")
                .password("tua565nh")
                .email("tuanhdai4@gmail.com")
                .location("New York")
                .dob(dob)
                .build();

        userResponse = UserResponse.builder()
                .id("035b3f35-0a06-449b-88f5-3142cda94c0a")
                .username("abc61")
                .email("tuanhdai4@gmail.com")
                .firstName("John")
                .lastName("Doe")
                .location("New York")
                .dob(dob)
                .createAt(createAt)
                .build();

        user = User.builder()
                .id("035b3f35-0a06-449b-88f5-3142cda94c0a")
                .username("abc61")
                .email("tuanhdai4@gmail.com")
                .firstName("John")
                .lastName("Doe")
                .location("New York")
                .dob(dob)
                .createAt(createAt)
                .build();
    }

    @Test
    void createUser_validRequest_success() {
        //GIVEN
        Role roleUser = new Role();
        roleUser.setName("USER");

        Mockito.when(userRepository.existsByUsername(ArgumentMatchers.anyString())).thenReturn(false);
        Mockito.when(userRepository.save(ArgumentMatchers.any())).thenReturn(user);
        Mockito.when(roleRepository.findByName("USER")).thenReturn(Optional.of(roleUser)); // Giả lập role USER
        //WHEN
        var response = userService.createUser(request);
        //THEN
        Assertions.assertEquals("035b3f35-0a06-449b-88f5-3142cda94c0a",response.getId());
        Assertions.assertEquals("abc61",response.getUsername());
        // Co the viet them ...
    }

    @Test
    void createUser_userExisted_fail() {
        //GIVEN
        Mockito.when(userRepository.existsByUsername(ArgumentMatchers.anyString())).thenReturn(true);
        //WHEN
        var exception = assertThrows(AppException.class,
                () -> userService.createUser(request));
        // THEN
        Assertions.assertEquals(exception.getErrorCode().getCode(),1005);
    }
    @Test
    void createUser_roleNotFound_fail() {
        // GIVEN
        Mockito.when(roleRepository.findByName("USER")).thenReturn(Optional.empty());

        // WHEN
        var exception = assertThrows(AppException.class, () -> userService.createUser(request));

        // THEN
        Assertions.assertEquals(1009, exception.getErrorCode().getCode()); // Mã lỗi tương ứng cho ROLE_NOT_EXIST
    }
    @Test
    @WithMockUser(username = "abc61")
    void getInfo_valid_success() {
        //GIVEN
        Mockito.when(userRepository.findUserByUsername(ArgumentMatchers.anyString())).thenReturn(Optional.of(user));
        //WHEN
        var response = userService.getMyInfo();
        //THEN
        Assertions.assertEquals(response.getUsername(),"abc61" );

    }
    @Test
    @WithMockUser(username = "abc61")
    void getInfo_userNotFound_error () {
        Mockito.when(userRepository.findUserByUsername(ArgumentMatchers.anyString())).thenReturn(Optional.ofNullable(null));
        var exception = assertThrows(AppException.class,
                () -> userService.getMyInfo());
        Assertions.assertEquals(1005, exception.getErrorCode().getCode());
    }

}
