package java_spring.job_hub.mapper;

import java_spring.job_hub.dto.request.CompaniesRequest;
import java_spring.job_hub.dto.request.CompaniesUpdateRequest;
import java_spring.job_hub.dto.response.CompaniesResponse;
import java_spring.job_hub.entity.Companies;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CompaniesMapper {
    @Mapping(target = "jobsList", ignore = true)
    @Mapping(target = "companyServicesList", ignore = true)
    @Mapping(target = "imagesList", ignore = true)
    @Mapping(target = "recruitersList", ignore = true)
    Companies toCompanies(CompaniesRequest request);

    @Mapping(ignore = true, target = "jobsList")
    @Mapping(ignore = true, target = "companyServicesList")
    @Mapping(ignore = true, target = "imagesList")
    @Mapping(ignore = true, target = "recruitersList")
    void updateCompanies(@MappingTarget Companies companies, CompaniesUpdateRequest request);

    CompaniesResponse toCompaniesResponse(Companies companies);
}
