package com.ssafy.solvedpick.image.service;

import com.ssafy.solvedpick.image.domain.Image;
import com.ssafy.solvedpick.image.repository.ImageRepository;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.s3.dto.PresignedUrlResponse;
import com.ssafy.solvedpick.s3.service.S3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

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
    public void saveImageInfo(String originalFilename, String storedFilename, Member member) {

        Image image = Image.builder()
                .member(member)
                .originalFilename(originalFilename)
                .storedFilename(storedFilename)
                .build();

        imageRepository.save(image);
        log.debug("Saved image info - originalFilename: {}, storedFilename: {}, member: {}",
                originalFilename, storedFilename, member);
    }
}