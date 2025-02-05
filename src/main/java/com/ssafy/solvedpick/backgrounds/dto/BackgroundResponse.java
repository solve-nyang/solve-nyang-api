package com.ssafy.solvedpick.backgrounds.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BackgroundResponse {
    private List<BackgroundInfo> backgrounds;

    public static BackgroundResponse from(List<BackgroundInfo> backgrounds) {
        return BackgroundResponse.builder()
                .backgrounds(backgrounds)
                .build();
    }
}