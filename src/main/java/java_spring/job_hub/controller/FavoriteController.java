package java_spring.job_hub.controller;

import java_spring.job_hub.dto.request.FavoriteRequest;
import java_spring.job_hub.dto.response.ApiResponse;
import java_spring.job_hub.dto.response.FavoriteResponse;
import java_spring.job_hub.service.FavoriteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor // tu tao constructor khong cần autowired
@RequestMapping("/api/favorites")
public class FavoriteController {
    @Autowired
    FavoriteService favoriteService;

    @PostMapping("/add")
    public ApiResponse<FavoriteResponse> addFavorite(@RequestBody FavoriteRequest request) {
        return ApiResponse.<FavoriteResponse>builder()
                .result(favoriteService.addFavorite(request))
                .build();
    }
}
