package com.ssafy.solvedpick.gacha.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class HasEventAvatarResponseDTO {

    private Boolean hasEventAvatar;
}
