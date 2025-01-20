package com.ssafy.solvedpick.global.error.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.solvedpick.global.error.exception.ApiResponseException;
import com.ssafy.solvedpick.global.error.exception.UserInfoErrorException;
import com.ssafy.solvedpick.global.error.exception.VerificationNotFoundException;
import com.ssafy.solvedpick.global.error.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
            .getAllErrors()
            .get(0)
            .getDefaultMessage();
            
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.builder()
            		.message(errorMessage)
            		.build());
    }
    
    @ExceptionHandler({
        UserInfoErrorException.class,
        VerificationNotFoundException.class,
        ApiResponseException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequestExceptions(RuntimeException ex) {
        
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.builder()
            		.message(ex.getMessage())
            		.build());
    }
}