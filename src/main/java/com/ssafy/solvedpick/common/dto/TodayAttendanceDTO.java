package com.ssafy.solvedpick.common.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class TodayAttendanceDTO {
    private boolean isAttended;
}
