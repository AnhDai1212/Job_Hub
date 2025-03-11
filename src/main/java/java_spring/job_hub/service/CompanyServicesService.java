package java_spring.job_hub.service;

import java.util.List;
import java.util.stream.Collectors;
import java_spring.job_hub.dto.request.CompaniesServiceRequest;
import java_spring.job_hub.dto.response.CompaniesServiceResponse;
import java_spring.job_hub.entity.Companies;
import java_spring.job_hub.entity.CompanyServiceDetail;
import java_spring.job_hub.exception.AppException;
import java_spring.job_hub.exception.ErrorCode;
import java_spring.job_hub.mapper.CompaniesServiceMapper;
import java_spring.job_hub.repository.CompaniesRepository;
import java_spring.job_hub.repository.CompaniesServiceRepository;

import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompanyServicesService {
    CompaniesServiceRepository companiesServiceRepository;
    CompaniesServiceMapper companiesServiceMapper;
    CompaniesRepository companiesRepository;

    public List<CompaniesServiceResponse> addService(Integer companyId, List<CompaniesServiceRequest> serviceRequests) {
        Companies company = companiesRepository
                .findById(companyId)
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_FOUND));

        List<CompanyServiceDetail> services = serviceRequests.stream()
                .map(request -> {
                    CompanyServiceDetail companyServices = new CompanyServiceDetail();
                    companyServices.setCompanies(company);
                    companyServices.setServiceName(request.getServiceName());
                    companyServices.setDescription(request.getDescription());
                    return companyServices;
                })
                .collect(Collectors.toList());

        return companiesServiceMapper.toCompaniesServiceResponse(companiesServiceRepository.saveAll(services));
    }
}
