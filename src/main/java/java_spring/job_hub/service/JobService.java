package java_spring.job_hub.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java_spring.job_hub.dto.request.JobRequest;
import java_spring.job_hub.dto.request.JobUpdateRequest;
import java_spring.job_hub.dto.response.JobResponse;
import java_spring.job_hub.entity.JobCategories;
import java_spring.job_hub.entity.JobTag;
import java_spring.job_hub.entity.Jobs;
import java_spring.job_hub.entity.Recruiters;
import java_spring.job_hub.enums.JobsStatus;
import java_spring.job_hub.enums.RecruiterStatus;
import java_spring.job_hub.exception.AppException;
import java_spring.job_hub.exception.ErrorCode;
import java_spring.job_hub.mapper.JobMapper;
import java_spring.job_hub.repository.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class JobService {
    JobMapper jobMapper;
    JobRepository jobRepository;
    ApplicationsRepository applicationsRepository;
    FavoritesRepository favoritesRepository;
    RecruitersRepository recruitersRepository;
    CompaniesRepository companiesRepository;
    JobTagRepository jobTagRepository;
    JobCategoriesRepository jobCategoriesRepository;

    public JobResponse createJob(JobRequest request) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = jwt.getId(); // Lay userID tu jwt va ghi doi tuong recruiter vao nguoi dang bai
        //        log.info("UserId : " + userId);

        Recruiters recruiter = recruitersRepository
                .findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.RECRUITER_NOT_FOUND));
        log.info("Status: " + recruiter.getStatus());
        if (!RecruiterStatus.APPROVED.equals(
                recruiter.getStatus())) { // Ke ca khi data la enum hay chuyen sang string lai van chay dc
            throw new AppException(ErrorCode.RECRUITER_NOT_POST);
        }

        Jobs jobs = jobMapper.toJob(request);
        if (recruiter.getCompanies() != null) {
            jobs.setCompanies(recruiter.getCompanies());
        } else {
            throw new AppException(ErrorCode.RECRUITER_NO_COMPANY);
        }

        jobs.setCreateAt(LocalDate.now());
        jobs.setStatus(JobsStatus.PENDING.toString());
        jobs.setRecruiters(recruiter);
//        jobRepository.save(jobs);
        // Xử lý tags
        if (request.getJobTags() != null && !request.getJobTags().isEmpty()) {
            Set<JobTag> jobTags = new HashSet<>();
            for (String tagName : request.getJobTags()) {
                JobTag jobTag = jobTagRepository.findByTagName(tagName)
                        .orElseGet(() -> {
                            JobTag newTag = new JobTag();
                            newTag.setTagName(tagName);
                            return jobTagRepository.save(newTag);
                        });
                jobTags.add(jobTag);
            }
            jobs.setJobTags(jobTags);
        }
//        categories
        if(request.getJobCategories() != null && !request.getJobCategories().isEmpty()) {
            Set<JobCategories> jobCategoriesList = new HashSet<>();
            for(String categoriesName : request.getJobCategories()) {
                JobCategories jobCategories = jobCategoriesRepository.findByCategoryName(categoriesName).orElseGet(
                        ()-> {
                            JobCategories newCategories = new JobCategories();
                            newCategories.setCategoryName(categoriesName);
                            return jobCategoriesRepository.save(newCategories);
                        });
                jobCategoriesList.add(jobCategories);
            }
            jobs.setJobCategories(jobCategoriesList);
        }

        jobRepository.save(jobs);
        JobResponse response = jobMapper.toJobResponse(jobs);
        response.setApplicationCount(applicationsRepository.countApplicationsByJobId(jobs.getJobId()));
        response.setFavoriteCount(favoritesRepository.countFavoritesByJobId(jobs.getJobId()));

        return response;
    }

    public JobResponse updateJob(Integer jobId, JobUpdateRequest request) {
        Jobs job = jobRepository.findById(jobId)
                .orElseThrow(() -> new AppException(ErrorCode.JOB_NOT_FOUND));

        jobMapper.updateJob(job, request);

        //jobTags
        if (request.getJobTags() != null) { // Chỉ cập nhật nếu request có jobTags
            Set<JobTag> currentTags = job.getJobTags() != null ? job.getJobTags() : new HashSet<>();
            Set<JobTag> updatedTags = new HashSet<>();

            // Tạo hoặc tìm các tag từ request
            for (String tagName : request.getJobTags()) {
                JobTag jobTag = jobTagRepository.findByTagName(tagName)
                        .orElseGet(() -> {
                            JobTag newTag = new JobTag();
                            newTag.setTagName(tagName);
                            return jobTagRepository.save(newTag);
                        });
                updatedTags.add(jobTag);
            }

            // Đồng bộ tags: Xóa tag cũ không còn trong request, thêm tag mới
            currentTags.retainAll(updatedTags); // Giữ lại tag còn trong danh sách mới
            currentTags.addAll(updatedTags);
            job.setJobTags(currentTags);
        }

        if(request.getJobCategories() != null && !request.getJobCategories().isEmpty()) {
            Set<JobCategories> currentCategories = job.getJobCategories() != null ? job.getJobCategories(): new HashSet<>();
            Set<JobCategories> updateCategories = new HashSet<>();

            for(String categoriesName : request.getJobCategories()) {
                JobCategories jobCategories = jobCategoriesRepository.findByCategoryName(categoriesName).orElseGet(
                        ()-> {
                            JobCategories newCategories = new JobCategories();
                            newCategories.setCategoryName(categoriesName);
                            return jobCategoriesRepository.save(newCategories);
                        }
                );
                updateCategories.add(jobCategories);
            }
            currentCategories.retainAll(updateCategories);
            currentCategories.addAll(updateCategories);
            job.setJobCategories(currentCategories);
        }

        Jobs updatedJob = jobRepository.save(job);

        JobResponse response = jobMapper.toJobResponse(updatedJob);
        response.setApplicationCount(applicationsRepository.countApplicationsByJobId(updatedJob.getJobId()));
        response.setFavoriteCount(favoritesRepository.countFavoritesByJobId(updatedJob.getJobId()));

        return response;
    }

    public JobResponse getJob(Integer jobId) {
        Jobs job = jobRepository.findById(jobId).orElseThrow(() -> new AppException(ErrorCode.JOB_NOT_FOUND));
        return jobMapper.toJobResponse(job);
    }

    public void deleteJob(Integer jobId) {
        Jobs job = jobRepository.findById(jobId).orElseThrow(() -> new AppException(ErrorCode.JOB_NOT_FOUND));
        jobRepository.delete(job);
    }

    public Page<JobResponse> getListJobs(Pageable pageable) {
        return jobRepository.findAll(pageable).map(jobMapper::toJobResponse);
    }
}
