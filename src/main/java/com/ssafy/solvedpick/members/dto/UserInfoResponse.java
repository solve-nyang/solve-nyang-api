package com.ssafy.solvedpick.members.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoResponse {
    private String username;
    private int point;
    private String tier;
    private int solvedCount;
    private int streak;
}
