package java_spring.job_hub.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java_spring.job_hub.dto.request.CompaniesRequest;
import java_spring.job_hub.dto.request.CompaniesUpdateRequest;
import java_spring.job_hub.dto.response.CompaniesResponse;
import java_spring.job_hub.entity.Companies;
import java_spring.job_hub.exception.AppException;
import java_spring.job_hub.exception.ErrorCode;
import java_spring.job_hub.mapper.CompaniesMapper;
import java_spring.job_hub.repository.CompaniesRepository;
import java_spring.job_hub.repository.CompaniesServiceRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompaniesService {
    CompaniesRepository companiesRepository;
    CompaniesMapper companiesMapper;
    CompaniesServiceRepository companiesServiceRepository;
    CloudinaryService cloudinaryService;

    public CompaniesResponse createCompanies(CompaniesRequest request) {
        Companies companies = companiesMapper.toCompanies(request);
        companies.setCreateAt(new Date());
        return companiesMapper.toCompaniesResponse(companiesRepository.save(companies));
    }

    public CompaniesResponse getCompany(Integer companyId) {
        Companies companies = companiesRepository
                .findById(companyId)
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_FOUND));
        return companiesMapper.toCompaniesResponse(companies);
    }

    public List<CompaniesResponse> getAllCompanies() {

        return companiesRepository.findAll().stream()
                .map(companiesMapper::toCompaniesResponse)
                .toList();
    }

    public CompaniesResponse updateCompany(Integer companyId, CompaniesUpdateRequest request, MultipartFile image)
            throws IOException {
        Companies companies = companiesRepository
                .findById(companyId)
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_FOUND));
        companiesMapper.updateCompanies(companies, request);
        if (image != null && !image.isEmpty()) {
            companies.setAvatarUrl(cloudinaryService.uploadImage(image));
        }
        return companiesMapper.toCompaniesResponse(companiesRepository.save(companies));
    }
}
