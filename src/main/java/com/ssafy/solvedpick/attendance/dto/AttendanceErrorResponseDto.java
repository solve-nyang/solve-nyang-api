package com.ssafy.solvedpick.attendance.dto;

import com.ssafy.solvedpick.attendance.exception.AttendanceErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AttendanceErrorResponseDto {
    private final String code;
    private final String message;

    public static AttendanceErrorResponseDto of(AttendanceErrorCode errorCode) {
        return AttendanceErrorResponseDto.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }
} 