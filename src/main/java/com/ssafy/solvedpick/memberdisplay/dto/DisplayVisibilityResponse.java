package com.ssafy.solvedpick.memberdisplay.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DisplayVisibilityResponse {
    private String title;
    private Boolean titleVisible;
    private Boolean tierVisible;
    private Boolean memberClassVisible;
    private Boolean solvedCountVisible;
    private Boolean streakVisible;
}
