package com.ssafy.solvedpick.common.error.handler;

import com.ssafy.solvedpick.common.error.exception.UserInfoErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.solvedpick.common.error.exception.ApiResponseException;
import com.ssafy.solvedpick.common.error.exception.VerificationNotFoundException;
import com.ssafy.solvedpick.common.error.dto.ErrorResponse;

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