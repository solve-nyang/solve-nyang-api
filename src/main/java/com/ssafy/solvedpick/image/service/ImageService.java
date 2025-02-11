package com.ssafy.solvedpick.image.service;

import com.ssafy.solvedpick.image.domain.Image;
import com.ssafy.solvedpick.image.dto.*;
import com.ssafy.solvedpick.image.repository.ImageRepository;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.image.dto.PresignedUrlResponse;
import com.ssafy.solvedpick.s3.service.S3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {
    private final S3Service s3Service;
    private final ImageRepository imageRepository;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String VOTE_KEY_PREFIX = "vote:";

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
                    String[] parts = image.getStoredFilename().split("-", 6);
                    String uuid = String.join("-", Arrays.copyOfRange(parts, 0, 5));
                    String fileKey = "contest/"+ uuid + "-" + image.getOriginalFilename();

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

    @Transactional
    public void voteImage(Member member, Long imageId) {
        LocalDateTime now = LocalDateTime.now();
        String today = now.format(DateTimeFormatter.ISO_DATE);
        String key = VOTE_KEY_PREFIX + member.getId() + ":" + imageId + ":" + today;

        LocalDateTime tomorrow = now.plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        Duration timeUntilMidnight = Duration.between(now, tomorrow);

        LocalDateTime testExpirationTime = now.plusMinutes(5);
        Duration testTimeUntilExpiration = Duration.between(now, testExpirationTime);

        Boolean testIsFirstVoteToday = redisTemplate.opsForValue()
                .setIfAbsent(key, "voted", testTimeUntilExpiration.toSeconds(), TimeUnit.SECONDS);

        Boolean isFirstVoteToday = redisTemplate.opsForValue()
                .setIfAbsent(key, "voted", timeUntilMidnight.toSeconds(), TimeUnit.SECONDS);

        if (Boolean.FALSE.equals(testIsFirstVoteToday)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "오늘은 더 이상 투표할 수 없습니다.");
        }

        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new HttpClientErrorException(
                        HttpStatus.NOT_FOUND,
                        "해당 이미지가 존재하지 않습니다."
                ));
        image.updateVoteCount();
    }

    public ImageCountResponse getVoteCount() {
        List<ImageCountDTO> voteCounts = imageRepository.findImageByVisibleTrue()
                .stream()
                .map(image -> ImageCountDTO.builder()
                        .imageId(image.getId())
                        .count(image.getVoteCount())
                        .build())
                .toList();

        return ImageCountResponse.builder()
                .voteCounts(voteCounts)
                .build();
    }
}