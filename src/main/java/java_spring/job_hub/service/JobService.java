package java_spring.job_hub.service;

import java_spring.job_hub.dto.request.JobRequest;
import java_spring.job_hub.dto.response.JobResponse;
import java_spring.job_hub.entity.Jobs;
import java_spring.job_hub.mapper.JobMapper;
import java_spring.job_hub.repository.ApplicationsRepository;
import java_spring.job_hub.repository.FavoritesRepository;
import java_spring.job_hub.repository.JobRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobService {
    JobMapper jobMapper;
    JobRepository jobRepository;
    ApplicationsRepository applicationsRepository;
    FavoritesRepository favoritesRepository;

    public JobResponse createJob(JobRequest request) {
        Jobs jobs = jobMapper.toJob(request);
        jobs.setCreateAt(LocalDate.now());
        jobRepository.save(jobs);

        JobResponse response = jobMapper.toJobResponse(jobs);
        response.setApplicationCount(applicationsRepository.countApplicationsByJobId(jobs.getJobId()));
        response.setFavoriteCount(favoritesRepository.countFavoritesByJobId(jobs.getJobId()));

        return response;
    }
}
