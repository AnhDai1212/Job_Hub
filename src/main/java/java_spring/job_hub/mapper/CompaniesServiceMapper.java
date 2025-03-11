package java_spring.job_hub.mapper;

import java.util.List;
import java_spring.job_hub.dto.request.CompaniesServiceRequest;
import java_spring.job_hub.dto.request.CompaniesServiceUpdateRequest;
import java_spring.job_hub.dto.response.CompaniesServiceResponse;
import java_spring.job_hub.entity.CompanyServiceDetail;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CompaniesServiceMapper {
    //    @Mapping(target = "", ignore = true)
    List<CompanyServiceDetail> toEntity(List<CompaniesServiceRequest> request);

    List<CompaniesServiceResponse> toCompaniesServiceResponse(List<CompanyServiceDetail> companyServices);

    @Mapping(target = "serviceId", ignore = true)
    CompaniesServiceResponse updateCompaniesService(CompaniesServiceUpdateRequest request);
}
