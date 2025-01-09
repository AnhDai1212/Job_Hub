package java_spring.job_hub.service;

import java_spring.job_hub.dto.request.UserCreationRequest;
import java_spring.job_hub.dto.response.UserResponse;
import java_spring.job_hub.entity.User;
import java_spring.job_hub.exception.AppException;
import java_spring.job_hub.mapper.UserMapper;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private UserCreationRequest request;
    private UserResponse userResponse;
    private User user;
    private LocalDate dob;

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
        Mockito.when(userRepository.existsByUsername(ArgumentMatchers.anyString())).thenReturn(false);
        Mockito.when(userRepository.save(ArgumentMatchers.any())).thenReturn(user);
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
        //THEN
        Assertions.assertEquals("035b3f35-0a06-449b-88f5-3142cda94c0a",userResponse.getId());
        Assertions.assertEquals("abc61",userResponse.getUsername());
        // THEN
        Assertions.assertEquals(exception.getErrorCode().getCode(),1005);
    }

}
