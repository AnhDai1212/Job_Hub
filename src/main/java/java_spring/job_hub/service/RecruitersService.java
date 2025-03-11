package java_spring.job_hub.service;

import java.util.Set;
import java_spring.job_hub.dto.response.RecruitersResponse;
import java_spring.job_hub.entity.Recruiters;
import java_spring.job_hub.entity.Role;
import java_spring.job_hub.entity.User;
import java_spring.job_hub.exception.AppException;
import java_spring.job_hub.exception.ErrorCode;
import java_spring.job_hub.mapper.RecruitersMapper;
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

    public RecruitersResponse createRecruiters(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        boolean hasEmployerRole = user.getRoles().stream().anyMatch(role -> "EMPLOYER".equals(role.getName()));
        if (hasEmployerRole) {
            throw new AppException(ErrorCode.ALREADY_EMPLOYER);
        }
        Role role = roleRepository.findByName("EMPLOYER").orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXIST));
        Set<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        Recruiters recruiters = Recruiters.builder().user(user).role(role).build();
        return recruitersMapper.toRecruitersResponse(recruitersRepository.save(recruiters));
    }
}
