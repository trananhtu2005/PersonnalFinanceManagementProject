package com.personalfinance.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "AUTH_001", "Email is invalid!"),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "AUTH_002", "Password doesn't match the confirmation password!"),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "AUTH_003", "Email is already in use!"),
    USERNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "AUTH_004", "Username is already in use!"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "AUTH_005", "Invalid email/username or password!"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_006", "Token is invalid!"),
    OTP_REQUEST_TOO_FREQUENT(HttpStatus.TOO_MANY_REQUESTS, "AUTH_007", "Please wait before requesting a new OTP!"),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "AUTH_008", "Email is not found!"),
    INVALID_OTP(HttpStatus.BAD_REQUEST, "AUTH_009", "OTP is invalid!"),
    OTP_EXPIRED(HttpStatus.BAD_REQUEST, "AUTH_010", "OTP is expired!"),
    OTP_ALREADY_USED(HttpStatus.BAD_REQUEST, "AUTH_011", "OTP is used!"),
    EMAIL_SENDER_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "MAIL_001", "Failed to send OTP email!");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
