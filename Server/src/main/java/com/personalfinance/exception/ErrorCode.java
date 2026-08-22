package com.personalfinance.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "AUTH_001", "Email is invalid!"),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "AUTH_002", "Password doesn't match the confirmation password!"),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "AUTH_003", "Email already in use!"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "AUTH_005", "Invalid email/username or password!"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_006", "Token is invalid!"),
    OTP_REQUEST_TOO_FREQUENT(HttpStatus.TOO_MANY_REQUESTS, "AUTH_007", "Please wait before requesting a new OTP!"),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "AUTH_008", "Email is not found!"),
    INVALID_OTP(HttpStatus.BAD_REQUEST, "AUTH_009", "OTP is invalid!"),
    OTP_EXPIRED(HttpStatus.BAD_REQUEST, "AUTH_010", "OTP is expired!"),
    OTP_ALREADY_USED(HttpStatus.BAD_REQUEST, "AUTH_011", "OTP is used!"),
    OLD_PASSWORD_INCORRECT(HttpStatus.BAD_REQUEST, "USER_001", "Old password is incorrect!"),
    EMAIL_SENDER_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "MAIL_001", "Failed to send OTP email!"),
    USERNAME_CHANGE_TOO_FREQUENT(HttpStatus.BAD_REQUEST, "USERNAME_001", "Username can only be changed once every 7 days!"),
    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND, "USERNAME_002", "Username is not found!"),
    USERNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "USERNAME_003", "Username already in use!"),
    CATEGORY_ALREADY_EXISTS(HttpStatus.CONFLICT, "CATEGORY_001", "Category already exists!"),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "CATEGORY_002", "Category is not found!"),
    COLOR_NOT_FOUND(HttpStatus.NOT_FOUND, "COLOR_001", "Color is not found!"),
    NO_DATA_TO_UPDATE(HttpStatus.BAD_REQUEST, "COMMON_001", "No data to update!"),
    WALLET_ALREADY_EXISTS(HttpStatus.CONFLICT, "WALLET_001", "Wallet already exists!"),
    WALLET_NOT_FOUND(HttpStatus.NOT_FOUND, "WALLET_002", "Wallet is not found!"),
    COLOR_NAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "COLOR_002", "Color name already exists!"),
    COLOR_CODE_ALREADY_EXISTS(HttpStatus.CONFLICT, "COLOR_003", "Color code already exists!"),
    SAVING_GOAL_NOT_FOUND(HttpStatus.NOT_FOUND, "SAVING_GOAL_001", "Saving goal is not found!"),
    INVALID_SAVING_CATEGORY(HttpStatus.BAD_REQUEST, "CATEGORY_003", "Category is not a saving category!"),
    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "NOTIFICATION_001", "Notification is not found!"),
    PAYMENT_REMINDER_NOT_FOUND(HttpStatus.NOT_FOUND, "PAYMENT_REMINDER_001", "Payment reminder is not found!"),
    TRANSACTION_NOT_FOUND(HttpStatus.NOT_FOUND, "TRANSACTION_001", "Transaction is not found!"),
    INVALID_MONTH(HttpStatus.BAD_REQUEST, "COMMON_002", "Month must be between 1 and 12!"),
    INVALID_YEAR(HttpStatus.BAD_REQUEST, "COMMON_003", "Year must be at least 1970 and cannot exceed the current year!"),
    BUDGET_ALREADY_EXISTS(HttpStatus.CONFLICT, "BUDGET_001", "Budget already exists!"),
    BUDGET_NOT_FOUND(HttpStatus.NOT_FOUND, "BUDGET_002", "Budget is not found!"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_004", "An unexpected error occured!"),
    AI_ANALYSIS_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "AI_001", "AI analysis failed!"),
    INSUFFICIENT_BALANCE(HttpStatus.BAD_REQUEST, "WALLET_003", "Insufficient wallet balance!");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
