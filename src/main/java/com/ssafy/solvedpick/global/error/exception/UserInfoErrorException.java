package com.ssafy.solvedpick.global.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserInfoErrorException extends RuntimeException {
    public UserInfoErrorException(String message) {
        super(message);
    }
}