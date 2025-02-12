package com.ssafy.solvedpick.common.error.exception.attendance;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AttendanceException extends RuntimeException {
    public AttendanceException(String message) {
        super(message);
    }
} 