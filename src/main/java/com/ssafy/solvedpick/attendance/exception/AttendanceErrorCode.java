package com.ssafy.solvedpick.attendance.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AttendanceErrorCode {
    
    ALREADY_ATTENDED(HttpStatus.BAD_REQUEST, "ATT-001", "이미 오늘 출석체크를 완료했습니다."),
    NO_NEW_SOLVED_PROBLEM(HttpStatus.BAD_REQUEST, "ATT-002", "새로 해결한 문제가 없습니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "ATT-003", "잘못된 출석체크 요청입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
} 