package java_spring.job_hub.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java_spring.job_hub.dto.request.RecruiterUpdateRequest;
import java_spring.job_hub.dto.response.ApiResponse;
import java_spring.job_hub.dto.response.RecruitersResponse;
import java_spring.job_hub.service.RecruitersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/recruiters")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecruitersController {
    @Autowired
    RecruitersService recruitersService;

    @PostMapping("/{userId}")
    ApiResponse<RecruitersResponse> createRecruiters(@PathVariable String userId) {
        return ApiResponse.<RecruitersResponse>builder()
                .result(recruitersService.createRecruiters(userId))
                .build();
    }

    @PutMapping("/select-company")
    public ApiResponse<RecruitersResponse> selectCompany(@RequestParam String userId, @RequestParam Integer companyId) {

        return ApiResponse.<RecruitersResponse>builder()
                .result(recruitersService.selectCompany(userId, companyId))
                .build();
    }

    @PutMapping("{recruiterId}")
    public ApiResponse<RecruitersResponse> updateRecruiter(
            @PathVariable Integer recruiterId, // Lấy ID từ đường dẫn
            @RequestBody RecruiterUpdateRequest request) {
        return ApiResponse.<RecruitersResponse>builder()
                .result(recruitersService.updateRecruiter(recruiterId, request))
                .build();
    }

//    @GetMapping()
//    public ApiResponse<List<RecruitersResponse>> getAllRecruiters() {
//        return ApiResponse.<List<RecruitersResponse>>builder()
//                .result(recruitersService.getListRecruiters())
//                .build();
//    }

    @GetMapping    // Load danh sach hay hon
    public ApiResponse<Page<RecruitersResponse>> getRecruiters(
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        Page<RecruitersResponse> recruiters = recruitersService.getListRecruiters(pageable);
        return ApiResponse.<Page<RecruitersResponse>>builder()
                .message("Success")
                .result(recruiters)
                .build();
    }

    @GetMapping("/{recruiterId}")
    public ApiResponse<RecruitersResponse> getRecruiterById(@PathVariable Integer recruiterId) {
        return ApiResponse.<RecruitersResponse>builder()
                .result(recruitersService.getRecruiterById(recruiterId))
                .build();
    }

    @DeleteMapping("/{recruiterId}")
    public ApiResponse<Void> deleteRecruiterById(@PathVariable Integer recruiterId) {
        recruitersService.deleteRecruiterById(recruiterId);
        return ApiResponse.<Void>builder()
                .message("Delete success!")
                .build();
    }
}
