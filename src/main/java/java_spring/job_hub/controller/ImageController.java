package java_spring.job_hub.controller;

import java.io.IOException;
import java_spring.job_hub.dto.response.ApiResponse;
import java_spring.job_hub.service.CloudinaryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor // tu tao constructor khong cần autowired
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/upload")
    public ApiResponse<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        String imageUrl = cloudinaryService.uploadImage(file);
        return ApiResponse.<String>builder()
                .result(imageUrl)
                .message("Upload successful")
                .build();
    }
}
