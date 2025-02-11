package com.ssafy.solvedpick.attendance.exception;

import lombok.Getter;

@Getter
public class AttendanceException extends RuntimeException {
    
    private final AttendanceErrorCode errorCode;

    public AttendanceException(AttendanceErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
} 