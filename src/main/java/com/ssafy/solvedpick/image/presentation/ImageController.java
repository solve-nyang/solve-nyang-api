package com.ssafy.solvedpick.image.presentation;

import com.ssafy.solvedpick.auth.service.AuthService;
import com.ssafy.solvedpick.image.dto.ContestImageResponse;
import com.ssafy.solvedpick.image.dto.ImageSaveRequest;
import com.ssafy.solvedpick.image.service.ImageService;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.image.dto.PresignedUrlResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final AuthService authService;

    @GetMapping("/presigned-url")
    public ResponseEntity<PresignedUrlResponse> getPresignedUrl(
            @RequestParam String filename,
            @RequestParam String contentType) {
        log.debug("Requesting presigned URL for filename: {}, contentType: {}", filename, contentType);
        return ResponseEntity.ok(imageService.getPresignedUrl(filename, contentType));
    }

    @PostMapping()
    public ResponseEntity<?> saveImage(@RequestBody ImageSaveRequest imageSaveRequest) {
        log.debug("Saving image info - originalFilename: {}, storedFilename: {}",
                imageSaveRequest.getOriginalFilename(), imageSaveRequest.getStoredFilename());

        Member member =  authService.getCurrentMember();

        imageService.saveImage(imageSaveRequest, member);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/contest")
    public ResponseEntity<ContestImageResponse> getContestImages() {
        ContestImageResponse images = imageService.findContestImages();
        return ResponseEntity.ok(images);
    }
}
