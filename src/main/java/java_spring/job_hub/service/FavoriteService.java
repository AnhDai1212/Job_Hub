package java_spring.job_hub.service;

import java_spring.job_hub.dto.request.FavoriteRequest;
import java_spring.job_hub.dto.response.FavoriteResponse;
import java_spring.job_hub.entity.Favorites;
import java_spring.job_hub.entity.Jobs;
import java_spring.job_hub.entity.User;
import java_spring.job_hub.exception.AppException;
import java_spring.job_hub.exception.ErrorCode;
import java_spring.job_hub.mapper.FavoriteMapper;
import java_spring.job_hub.repository.FavoritesRepository;
import java_spring.job_hub.repository.JobRepository;
import java_spring.job_hub.repository.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FavoriteService {

    FavoritesRepository favoritesRepository;
    JobRepository jobRepository;
    FavoriteMapper favoriteMapper;
    UserRepository userRepository;

    public FavoriteResponse addFavorite(FavoriteRequest request) {
        // Lấy userId từ JWT
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = jwt.getId(); // Giả sử userId nằm trong sub
        log.info("userId" + userId);

        // Kiểm tra job có tồn tại không
        Integer jobId = request.getJobId();
        Jobs job = jobRepository.findById(jobId).orElseThrow(() -> new AppException(ErrorCode.JOB_NOT_FOUND));

        // Kiểm tra xem đã yêu thích chưa
        if (favoritesRepository.existsByUserIdAndJobs(userId, job)) {
            throw new AppException(ErrorCode.ALREADY_FAVORITED);
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        // Tạo mới Favorite
        Favorites favorite = Favorites.builder().user(user).jobs(job).createAt(new Date()).build();

        // Lưu vào database
        return favoriteMapper.toFavoriteResponse(favoritesRepository.save(favorite));
    }
}
