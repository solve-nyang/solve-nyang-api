package com.solvenyang.members.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileResponse {
    private String username;
    private Long point;
    private String tier;
    private int memberClass;
    private int solvedCount;
    private int streak;
}
