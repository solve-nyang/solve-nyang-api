package com.ssafy.solvedpick.ownedavatar.dto;

import lombok.Getter;

@Getter
public class AvatarSaleResponseDTO {
    private final int solvedCnt;
    private final int point;

    private AvatarSaleResponseDTO(int solvedCnt, int point) {
        this.solvedCnt = solvedCnt;
        this.point = point;
    }
    public static AvatarSaleResponseDTO of(int solvedCnt, int point) {
        return new AvatarSaleResponseDTO(solvedCnt, point);
    }
}
