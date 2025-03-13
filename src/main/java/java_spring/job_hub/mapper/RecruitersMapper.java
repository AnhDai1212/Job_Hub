package java_spring.job_hub.mapper;

import java.util.List;

import java_spring.job_hub.dto.request.RecruiterUpdateRequest;
import java_spring.job_hub.dto.request.RecruitersRequest;
import java_spring.job_hub.dto.request.UserUpdateRequest;
import java_spring.job_hub.dto.response.RecruitersResponse;
import java_spring.job_hub.entity.Recruiters;

import java_spring.job_hub.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

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

    @Mapping(target = "id", ignore = true) // Không cập nhật ID
    @Mapping(target = "companies", ignore = true) // Company xử lý riêng
    @Mapping(target = "user", ignore = true) // User không cần cập nhật
    @Mapping(target = "role", ignore = true) // Role không thay đổi
    void updateRecruiter(@MappingTarget  Recruiters recruiters, RecruiterUpdateRequest request);

    // Ánh xạ danh sách
//    <RecruitersResponse> toRecruitersResponseList(List<Recruiters> recruiters);
}
