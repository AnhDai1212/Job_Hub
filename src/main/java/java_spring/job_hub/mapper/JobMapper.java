package java_spring.job_hub.mapper;

import java_spring.job_hub.dto.request.JobRequest;
import java_spring.job_hub.dto.request.JobUpdateRequest;
import java_spring.job_hub.dto.response.JobResponse;
import java_spring.job_hub.entity.Jobs;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface JobMapper {

    @Mapping(target = "jobTags", ignore = true)
    @Mapping(target = "jobCategories", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "favoritesList", ignore = true)
    @Mapping(target = "applicationsList", ignore = true)
    @Mapping(target = "companies", ignore = true)
    @Mapping(target = "recruiters", ignore = true)
    Jobs toJob(JobRequest request);

    @Mapping(target = "applicationCount", ignore = true) // Không ánh xạ trực tiếp
    @Mapping(target = "favoriteCount", ignore = true) // Không ánh xạ trực tiếp
    JobResponse toJobResponse(Jobs job);

    @Mapping(target = "jobTags", ignore = true)
    @Mapping(target = "jobCategories", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "favoritesList", ignore = true)
    @Mapping(target = "applicationsList", ignore = true)
    @Mapping(target = "companies", ignore = true)
    @Mapping(target = "recruiters", ignore = true)
    void updateJob(@MappingTarget Jobs jobs, JobUpdateRequest request);
}
