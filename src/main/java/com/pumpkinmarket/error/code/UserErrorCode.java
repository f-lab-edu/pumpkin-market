package com.pumpkinmarket.error.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserErrorCode implements ErrorCode {
    ALREADY_EXISTS_USER(
            "ALREADY_EXISTS_USER",
            "이미 존재하는 유저입니다.",
            HttpStatus.CONFLICT.value()
    ),
    NOT_FOUND_USER(
            "NOT_FOUND_USER",
            "존재하지 않는 유저입니다.",
            HttpStatus.NOT_FOUND.value()
    );

    private final int httpStatus;
    private final String errorCode;
    private final String message;

    UserErrorCode(String errorCode, String message, int httpStatus) {
        this.errorCode = errorCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
