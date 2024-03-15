package com.pumpkinmarket.exception;

import com.pumpkinmarket.error.code.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;

    public BusinessException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessException(final ErrorCode errorCode, String exceptionMessage) {
        super(exceptionMessage);
        this.errorCode = errorCode;
    }
}
