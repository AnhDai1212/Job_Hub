package java_spring.job_hub.configuration;

import java_spring.job_hub.entity.Role;
import java_spring.job_hub.entity.User;
import java_spring.job_hub.enums.Roles;
import java_spring.job_hub.repository.RoleRepository;
import java_spring.job_hub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor  // tu tao constructor khong cần autowired
@Slf4j  // Sinh ra log
public class ApplicationInitConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository,
                                        RoleRepository roleRepository) {  // Add một USER_ADMIN khi chạy lên lần dầu
        return args -> {
            if(userRepository.findUserByUsername("tuanhdai").isEmpty()){
               Role adminRole = roleRepository.findByName(Roles.ADMIN.name()).orElseThrow(
                       () -> new RuntimeException("Role not found ADMIN"));
                HashSet<Role> roles = new HashSet<>();
                roles.add(adminRole);

                User user = User.builder()
                        .username("tuanhdai")
                        .password(passwordEncoder.encode("admin123"))
                        .roles(roles)
                        .build();
                userRepository.save(user);
                log.warn("Admin user has bean created with default password: admin, please change it!");
            }
        };
    }
}
