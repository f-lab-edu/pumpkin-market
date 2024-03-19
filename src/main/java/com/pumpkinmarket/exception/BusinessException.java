package com.pumpkinmarket.exception;

import com.pumpkinmarket.error.code.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;

    protected BusinessException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    protected BusinessException(final ErrorCode errorCode, String exceptionMessage) {
        super(exceptionMessage);
        this.errorCode = errorCode;
    }

    public static BusinessException of(final ErrorCode errorCode) {
        return new BusinessException(errorCode);
    }

    public static BusinessException of(final ErrorCode errorCode, String exceptionMessage) {
        return new BusinessException(errorCode, exceptionMessage);
    }
}
