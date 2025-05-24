package com.solvenyang.image.presentation;

import com.solvenyang.auth.service.AuthService;
import com.solvenyang.image.dto.*;
import com.solvenyang.image.service.ImageService;
import com.solvenyang.members.domain.Member;
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

    @PatchMapping("/vote/{imageId}")
    public ResponseEntity<?> voteImage(@PathVariable Long imageId) {
        Member member =  authService.getCurrentMember();
        imageService.voteImage(member, imageId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/votes")
    public ResponseEntity<ImageCountResponse> getVoteCount() {
        ImageCountResponse votes = imageService.getVoteCount();
        return ResponseEntity.ok(votes);
    }

    @GetMapping("/voted")
    public ResponseEntity<CheckVotedDTO> checkVoted() {
        Member member =  authService.getCurrentMember();
        CheckVotedDTO response = imageService.getCheckVoted(member);
        return ResponseEntity.ok(response);
    }

}
