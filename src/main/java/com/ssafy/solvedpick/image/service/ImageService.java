package com.ssafy.solvedpick.image.service;

import com.ssafy.solvedpick.image.domain.Image;
import com.ssafy.solvedpick.image.dto.ContestImageDTO;
import com.ssafy.solvedpick.image.dto.ContestImageResponse;
import com.ssafy.solvedpick.image.dto.ImageSaveRequest;
import com.ssafy.solvedpick.image.repository.ImageRepository;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.image.dto.PresignedUrlResponse;
import com.ssafy.solvedpick.s3.service.S3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {
    private final S3Service s3Service;
    private final ImageRepository imageRepository;

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/png",
            "image/svg+xml"
    );

    public PresignedUrlResponse getPresignedUrl(String filename, String contentType) {
        validateContentType(contentType);
        return PresignedUrlResponse.builder()
                .presignedUrl(s3Service.generatePresignedUrlForUpload(filename, contentType))
                .build();
    }

    private void validateContentType(String contentType) {
        if (!ALLOWED_CONTENT_TYPES.contains(contentType)) {
            throw new HttpClientErrorException(
                    HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                    "지원하지 않는 이미지 형식입니다."
            );
        }
    }

    @Transactional
    public void saveImage(ImageSaveRequest imageSaveRequest, Member member) {
        try {
            Image image = Image.builder()
                    .member(member)
                    .originalFilename(imageSaveRequest.getOriginalFilename())
                    .storedFilename(imageSaveRequest.getStoredFilename())
                    .visible(false)
                    .build();

            imageRepository.save(image);
            log.debug("Saved image info - originalFilename: {}, storedFilename: {}, member: {}",
                    imageSaveRequest.getOriginalFilename(), imageSaveRequest.getStoredFilename(), member);

        } catch (Exception e) {
            log.error("Failed to save image info", e);
            throw new HttpClientErrorException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "이미지 정보 저장에 실패했습니다."
            );
        }
    }

    public ContestImageResponse findContestImages() {
        List<ContestImageDTO> images = imageRepository.findImageByVisibleTrue()
                .stream()
                .map(image -> {
                    String uuid = image.getStoredFilename().split(image.getOriginalFilename())[0];
                    String fileKey = "contest/"+ uuid + image.getOriginalFilename();

                    return ContestImageDTO.builder()
                            .imageId(image.getId())
                            .presignedUrl(s3Service.generatePresignedUrlForDownload(fileKey))
                            .memberId(image.getMember().getId())
                            .username(image.getMember().getUsername())
                            .build();
                }).toList();

        return ContestImageResponse.builder()
                .images(images)
                .build();
    }
}