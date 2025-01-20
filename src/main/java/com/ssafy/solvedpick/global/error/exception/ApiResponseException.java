package com.ssafy.solvedpick.global.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApiResponseException extends RuntimeException {
    public ApiResponseException(String message) {
        super(message);
    }
}