package java_spring.job_hub.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java_spring.job_hub.dto.request.UserCreationRequest;
import java_spring.job_hub.dto.response.UserResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
@Testcontainers
public class UserControllerIntegrationTest { // test viet cho userController

    @Container
    static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>("mysql:latest");

    @DynamicPropertySource
    static void configureDtasource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
        registry.add("spring.datasource.driverClassName", () -> "com.mysql.cj.jdbc.Driver");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
    }

    @Autowired
    private MockMvc mockMvc;

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
    @WithMockUser
    void createUser_validRequest_success() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Đăng ký module

        String content = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000))
                //
                // .andExpect(MockMvcResultMatchers.jsonPath("result.id").value("4cf01cee-bea2-47b0-bd05-2f7ecbca48ea"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.username").value("abc61"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.location").value("New York"));
    }
}
