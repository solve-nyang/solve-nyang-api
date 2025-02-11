package com.ssafy.solvedpick.memberPromotion.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PromotionDrawResponseDTO {

    private String avatarName;
}
