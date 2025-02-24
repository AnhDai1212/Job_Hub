package java_spring.job_hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class JobHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobHubApplication.class, args);
    }
}
