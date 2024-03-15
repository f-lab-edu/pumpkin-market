package com.pumpkinmarket.error.code;

public interface ErrorCode {
    int getHttpStatus();
    String getErrorCode();
    String getMessage();
}