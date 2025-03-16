package java_spring.job_hub.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java_spring.job_hub.dto.request.RecruiterUpdateRequest;
import java_spring.job_hub.dto.response.RecruitersResponse;
import java_spring.job_hub.entity.Companies;
import java_spring.job_hub.entity.Recruiters;
import java_spring.job_hub.entity.Role;
import java_spring.job_hub.entity.User;
import java_spring.job_hub.enums.CompanyStatus;
import java_spring.job_hub.enums.RecruiterStatus;
import java_spring.job_hub.exception.AppException;
import java_spring.job_hub.exception.ErrorCode;
import java_spring.job_hub.mapper.RecruitersMapper;
import java_spring.job_hub.repository.CompaniesRepository;
import java_spring.job_hub.repository.RecruitersRepository;
import java_spring.job_hub.repository.RoleRepository;
import java_spring.job_hub.repository.UserRepository;

import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RecruitersService {
    RecruitersRepository recruitersRepository;
    UserRepository userRepository;
    RoleRepository roleRepository;
    RecruitersMapper recruitersMapper;
    CompaniesRepository companiesRepository;

    public RecruitersResponse createRecruiters(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        boolean hasEmployerRole = user.getRoles().stream().anyMatch(role -> "EMPLOYER".equals(role.getName()));
        if (hasEmployerRole) {
            throw new AppException(ErrorCode.ALREADY_EMPLOYER);
        }
        Role employerRole =
                roleRepository.findByName("EMPLOYER").orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXIST));
        user.getRoles().add(employerRole);
        userRepository.save(user);
        //        Set<Role> roles = user.getRoles();
        //        roles.add(employerRole);
        //        user.setRoles(roles);
        Recruiters recruiters =
                Recruiters.builder().user(user).role(employerRole).build();
        recruiters.setStatus(RecruiterStatus.PENDING);
        return recruitersMapper.toRecruitersResponse(recruitersRepository.save(recruiters));
    }

    public RecruitersResponse selectCompany(String userId, Integer companyId) {
        Companies companies = companiesRepository
                .findById(companyId)
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_FOUND));

        Recruiters recruiters = recruitersRepository
                .findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.RECRUITER_NOT_FOUND));
        if(!companies.getStatus().equals(CompanyStatus.APPROVED)) {
                throw new AppException(ErrorCode.COMPANY_NOT_FOUND);
        }

        recruiters.setCompanies(companies);
        //        recruiters.setStatus(RecruiterStatus.PENDING);

        return recruitersMapper.toRecruitersResponse(recruitersRepository.save(recruiters));
    }

    public RecruitersResponse updateRecruiter(Integer recruiterId, RecruiterUpdateRequest request) {
        Recruiters recruiters = recruitersRepository
                .findById(recruiterId)
                .orElseThrow(() -> new AppException(ErrorCode.RECRUITER_NOT_FOUND));

        recruitersMapper.updateRecruiter(recruiters, request);

        if (request.getCompanyId() != null) {
            Companies companies = companiesRepository
                    .findById(request.getCompanyId())
                    .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_FOUND));
            recruiters.setCompanies(companies);
        }
        return recruitersMapper.toRecruitersResponse(recruitersRepository.save(recruiters));
    }

//    public List<RecruitersResponse> getListRecruiters() {
//        return recruitersRepository.findAll().stream()
//                .map(recruitersMapper::toRecruitersResponse)
//                .toList();
//    }
    //Load danh sach hay hon
    public Page<RecruitersResponse> getListRecruiters(Pageable pageable) {
        return recruitersRepository.findAll(pageable).map(recruitersMapper::toRecruitersResponse);
    }

    public RecruitersResponse getRecruiterById(Integer recruiterId) {
        Recruiters recruiters = recruitersRepository
                .findById(recruiterId)
                .orElseThrow(() -> new AppException(ErrorCode.RECRUITER_NOT_FOUND));
        return recruitersMapper.toRecruitersResponse(recruiters);
    }

    public void deleteRecruiterById(Integer recruiterId) {
        Recruiters recruiters = recruitersRepository
                .findById(recruiterId)
                .orElseThrow(() -> new AppException(ErrorCode.RECRUITER_NOT_FOUND));
        recruitersRepository.delete(recruiters);
    }
}
