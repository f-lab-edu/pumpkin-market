package com.pumpkinmarket.error.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommonErrorCode implements ErrorCode {
    UNKNOWN_ERROR(
            "UNKNOWN_ERROR",
            "알 수 없는 에러",
            HttpStatus.INTERNAL_SERVER_ERROR.value()
    ),
    UNAUTHORIZED_ERROR(
            "UNAUTHORIZED_ERROR",
            "인증되지 않은 사용자 입니다.",
            HttpStatus.UNAUTHORIZED.value()
    ),
    VALIDATION_ERROR(
            "VALIDATION_ERROR",
            "validation error",
            HttpStatus.BAD_REQUEST.value()
    );

    private final int httpStatus;
    private final String errorCode;
    private final String message;

    CommonErrorCode(String errorCode, String message, int httpStatus) {
        this.errorCode = errorCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
