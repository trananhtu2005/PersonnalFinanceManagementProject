package com.personalfinance.exception;

import com.personalfinance.common.ApiErrorResponse;
import io.jsonwebtoken.JwtException;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiErrorResponse> handleAppException(AppException ex) {
        ErrorCode errorCode = ex.getErrorCode();

        ApiErrorResponse response = ApiErrorResponse
                .builder()
                .timestamp(LocalDateTime.now())
                .status(errorCode.getStatus().value())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        ErrorCode errorCode = ErrorCode.INVALID_CREDENTIALS;

        ApiErrorResponse response = ApiErrorResponse
                .builder()
                .timestamp(LocalDateTime.now())
                .status(errorCode.getStatus().value())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiErrorResponse> handleJwtException(JwtException ex) {
        ErrorCode errorCode = ErrorCode.INVALID_TOKEN;

        ApiErrorResponse response = ApiErrorResponse
                .builder()
                .timestamp(LocalDateTime.now())
                .status(errorCode.getStatus().value())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }
}
