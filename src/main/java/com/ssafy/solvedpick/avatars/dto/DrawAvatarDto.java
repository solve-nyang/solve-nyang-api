package com.ssafy.solvedpick.avatars.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DrawAvatarDto {
    private Long ownedAvatarId;
    private Long avatarId;
    private String name;
    private String rarity;
    private double dropRate;
}
