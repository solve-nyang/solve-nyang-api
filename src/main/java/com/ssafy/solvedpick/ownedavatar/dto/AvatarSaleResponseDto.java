package com.ssafy.solvedpick.ownedavatar.dto;

import lombok.Getter;

@Getter
public class AvatarSaleResponseDto {
    private final int solvedCnt;
    private final int point;

    private AvatarSaleResponseDto(int solvedCnt, int point) {
        this.solvedCnt = solvedCnt;
        this.point = point;
    }
    public static AvatarSaleResponseDto of(int solvedCnt, int point) {
        return new AvatarSaleResponseDto(solvedCnt, point);
    }
}
