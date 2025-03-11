package java_spring.job_hub.mapper;

import java.util.List;
import java_spring.job_hub.dto.request.RecruitersRequest;
import java_spring.job_hub.dto.response.RecruitersResponse;
import java_spring.job_hub.entity.Recruiters;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecruitersMapper {

    // Ánh xạ từ RecruitersRequest sang Recruiters
    @Mapping(source = "userId", target = "user.id") // Chuyển userId thành user.id
    Recruiters toRecruiters(RecruitersRequest request);

    // Ánh xạ từ Recruiters sang RecruitersResponse
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "role.name", target = "roleName")
    @Mapping(source = "companies.companyId", target = "companyId")
    RecruitersResponse toRecruitersResponse(Recruiters recruiters);

    // Ánh xạ danh sách
    List<RecruitersResponse> toRecruitersResponseList(List<Recruiters> recruiters);
}
