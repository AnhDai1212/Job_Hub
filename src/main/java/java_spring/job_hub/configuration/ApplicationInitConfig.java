package java_spring.job_hub.configuration;

import jakarta.annotation.PostConstruct;
import java_spring.job_hub.entity.Role;
import java_spring.job_hub.entity.User;
import java_spring.job_hub.enums.Roles;
import java_spring.job_hub.repository.RoleRepository;
import java_spring.job_hub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.TimeZone;

@Configuration
@RequiredArgsConstructor  // tu tao constructor khong cần autowired
@Slf4j  // Sinh ra log
public class ApplicationInitConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        // Thiết lập múi giờ mặc định
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
//        log.info("Default timezone set to Asia/Ho_Chi_Minh");
        Calendar calendar = Calendar.getInstance();  // Sử dụng múi giờ hệ thống
//        Date date = calendar.getTime();
//        System.out.println("Time: " + date);
    }
    @Bean
    @ConditionalOnProperty(prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver"
    )
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {  // Add một USER_ADMIN khi chạy lên lần dầu
          log.info("Init Application..........");

        return args -> {
            if(userRepository.findUserByUsername("tuanhdai").isEmpty()){
//               Role adminRole = roleRepository.findByName(Roles.ADMIN.name()).orElseThrow(
//                       () -> new RuntimeException("Role not found ADMIN"));
//                HashSet<Role> roles = new HashSet<>();
//                roles.add(adminRole);

//                Role adminRole = Role.builder()
//                        .name(Roles.ADMIN.name())
//                        .build();
//                HashSet<Role> roles =  new HashSet<>();
//                roles.add(adminRole);
                User user = User.builder()
                        .username("tuanhdai")
                        .password(passwordEncoder.encode("admin123"))
//                        .roles(roles)
                        .build();
                userRepository.save(user);
                log.warn("Admin user has bean created with default password: admin, please change it!");
            }
        };
    }
}
