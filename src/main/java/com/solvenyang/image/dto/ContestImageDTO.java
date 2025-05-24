package com.solvenyang.image.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ContestImageDTO {
    private Long imageId;
    private String presignedUrl;
    private Long memberId;
    private String username;
}
