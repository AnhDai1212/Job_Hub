package java_spring.job_hub.controller;

import java.io.IOException;
import java.util.List;

import com.cloudinary.Api;
import java_spring.job_hub.dto.request.CompaniesRequest;
import java_spring.job_hub.dto.request.CompaniesServiceRequest;
import java_spring.job_hub.dto.request.CompaniesUpdateRequest;
import java_spring.job_hub.dto.response.ApiResponse;
import java_spring.job_hub.dto.response.CompaniesResponse;
import java_spring.job_hub.dto.response.CompaniesServiceResponse;
import java_spring.job_hub.service.CompaniesService;
import java_spring.job_hub.service.CompanyServicesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor // tu tao constructor khong cần autowired
@RequestMapping("/api/companies")
public class CompaniesController {

    @Autowired
    CompaniesService companiesService;

    @Autowired
    CompanyServicesService companyServicesService;

    @PostMapping()
    public ApiResponse<CompaniesResponse> createCompanies(@RequestBody CompaniesRequest request) {
        return ApiResponse.<CompaniesResponse>builder()
                .result(companiesService.createCompanies(request))
                .build();
    }

    @PostMapping("/{companyId}/services")
    public ApiResponse<List<CompaniesServiceResponse>> addServicesToCompany(
            @PathVariable Integer companyId, @RequestBody List<CompaniesServiceRequest> serviceRequests) {

        List<CompaniesServiceResponse> companyServiceResponses =
                companyServicesService.addService(companyId, serviceRequests);

        return ApiResponse.<List<CompaniesServiceResponse>>builder()
                .result(companyServiceResponses)
                .build();
    }
    @GetMapping("/{companiesId}")
    public ApiResponse<CompaniesResponse> getCompany(@PathVariable Integer companiesId) {
        return ApiResponse.<CompaniesResponse>builder()
                .result(companiesService.getCompany(companiesId))
                .build();
    }

    @GetMapping()
    public ApiResponse<List<CompaniesResponse>> getAllCompanies() {
        return ApiResponse.<List<CompaniesResponse>>builder()
                .result(companiesService.getAllCompanies())
                .build();
    }

    @PutMapping(value = "/{companyId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse<CompaniesResponse> updateCompany(@PathVariable("companyId") Integer companyId,
                                                 @RequestPart("company")CompaniesUpdateRequest companiesUpdateRequest,
                                                 @RequestPart(value = "image", required = false)MultipartFile image) throws IOException {
        return ApiResponse.<CompaniesResponse>builder()
                .result(companiesService.updateCompany(companyId, companiesUpdateRequest, image))
                .build();
    }
}
