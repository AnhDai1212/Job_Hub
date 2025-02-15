package java_spring.job_hub.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java_spring.job_hub.dto.request.UserCreationRequest;
import java_spring.job_hub.dto.response.UserResponse;
import java_spring.job_hub.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
@TestPropertySource("/test.properties")
public class UserControllerTest { // test viet cho userController

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserCreationRequest request;
    private UserResponse userResponse;
    private LocalDate dob;
    LocalDateTime createAt;

    @BeforeEach
    void initData() {
        dob = LocalDate.of(2003, 2, 12);

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
    }

    @Test
    void createUser_validRequest_success() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Đăng ký module

        String content = objectMapper.writeValueAsString(request);

        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(userResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000));
        //
        // .andExpect(MockMvcResultMatchers.jsonPath("result.id").value("035b3f35-0a06-449b-88f5-3142cda94c0"));
    }

    @Test
    void create_usernameInvalid_fail() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Đăng ký module
        request.setUsername("ad");
        String content = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1004))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Username must be at 3 characters"));
    }
}
