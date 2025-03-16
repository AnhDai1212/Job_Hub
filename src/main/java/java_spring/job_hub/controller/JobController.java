package java_spring.job_hub.controller;

import java_spring.job_hub.dto.request.JobRequest;
import java_spring.job_hub.dto.request.JobUpdateRequest;
import java_spring.job_hub.dto.response.ApiResponse;
import java_spring.job_hub.dto.response.JobResponse;
import java_spring.job_hub.service.JobService;

import org.springframework.web.bind.annotation.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/jobs")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobController {

    JobService jobService;

    @PostMapping()
    public ApiResponse<JobResponse> createJob(@RequestBody JobRequest request) {

        return ApiResponse.<JobResponse>builder()
                .result(jobService.createJob(request))
                .build();
    }

    @PutMapping("/{jobId}")
    public ApiResponse<JobResponse> updateJob(@PathVariable Integer jobId, @RequestBody JobUpdateRequest request) {
        return ApiResponse.<JobResponse>builder()
                .result(jobService.updateJob(jobId, request))
                .build();
    }
}
