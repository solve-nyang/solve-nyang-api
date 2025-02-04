package com.ssafy.solvedpick.s3.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PresignedUrlResponse {
    private String presignedUrl;

    @Builder
    public PresignedUrlResponse(String presignedUrl) {
        this.presignedUrl = presignedUrl;
    }
}