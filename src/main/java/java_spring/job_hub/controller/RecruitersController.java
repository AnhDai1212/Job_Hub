package java_spring.job_hub.controller;

import java_spring.job_hub.dto.response.ApiResponse;
import java_spring.job_hub.dto.response.RecruitersResponse;
import java_spring.job_hub.service.RecruitersService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recruiters")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecruitersController {
    @Autowired
    RecruitersService recruitersService;
    @PostMapping("/{userId}")
    ApiResponse<RecruitersResponse> createRecruiters(@PathVariable  String userId) {
        return ApiResponse.<RecruitersResponse>builder()
                .result(recruitersService.createRecruiters(userId))
                .build();
    }



}
