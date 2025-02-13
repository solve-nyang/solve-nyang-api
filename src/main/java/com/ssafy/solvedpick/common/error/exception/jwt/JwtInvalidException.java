package com.ssafy.solvedpick.common.error.exception.jwt;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JwtInvalidException extends RuntimeException {
	public JwtInvalidException(String message) {
        super(message);
    }
}
