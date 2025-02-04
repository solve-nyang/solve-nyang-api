package com.ssafy.solvedpick.common.error.handler;

import com.ssafy.solvedpick.common.error.exception.UserInfoErrorException;
import com.ssafy.solvedpick.common.error.exception.jwt.JwtExpiredException;
import com.ssafy.solvedpick.common.error.exception.jwt.JwtInvalidException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.solvedpick.common.error.exception.*;
import com.ssafy.solvedpick.common.error.dto.ErrorResponse;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ErrorResponse> handleValidationExceptions(Exception ex) {
        String errorMessage = ex.getMessage();
            
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.builder()
            		.message(errorMessage)
            		.build());
    }
    
    @ExceptionHandler({
        UserInfoErrorException.class,
        VerificationNotFoundException.class,
        ApiResponseException.class,
        InvalidPasswordException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequestExceptions(RuntimeException ex) {
        
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.builder()
            		.message(ex.getMessage())
            		.build());
    }
    
    @ExceptionHandler({
        JwtExpiredException.class,
        JwtInvalidException.class
    })
    public ResponseEntity<ErrorResponse> handleJwtExceptions(RuntimeException ex) {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(ErrorResponse.builder()
                    .message(ex.getMessage())
                    .build());
    }
    
    @ExceptionHandler({
            HttpClientErrorException.class
    })
    public ResponseEntity<ErrorResponse> handleHttpClientErrorExceptions(HttpClientErrorException e) {
        return ResponseEntity
                .status(e.getStatusCode())
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build());
    }
}