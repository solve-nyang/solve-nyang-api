package com.ssafy.solvedpick.accounts.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoResponse {
    private String nickname;
    private int point;
    private int solvedacTier;
    private int solvedCount;
    private int solvedacStrick;
}
