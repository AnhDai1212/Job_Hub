package java_spring.job_hub.controller;

import java_spring.job_hub.dto.request.JobRequest;
import java_spring.job_hub.dto.request.JobUpdateRequest;
import java_spring.job_hub.dto.response.ApiResponse;
import java_spring.job_hub.dto.response.JobResponse;
import java_spring.job_hub.service.JobService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @Autowired
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

    @GetMapping("/{jobId}")
    public ApiResponse<JobResponse> getJob(@PathVariable Integer jobId) {
        return ApiResponse.<JobResponse>builder()
                .result(jobService.getJob(jobId))
                .build();
    }
    ;

    @GetMapping()
    public ApiResponse<Page<JobResponse>> getAllJob(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        Page<JobResponse> jobs = jobService.getListJobs(pageable);
        return ApiResponse.<Page<JobResponse>>builder().result(jobs).build();
    }
    ;

    @DeleteMapping("/{jobId}")
    public ApiResponse<Void> deleteJob(@PathVariable Integer jobId) {
        jobService.deleteJob(jobId);
        return ApiResponse.<Void>builder().message("Delete job success!").build();
    }

}
