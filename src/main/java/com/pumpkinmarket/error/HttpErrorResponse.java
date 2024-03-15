package com.pumpkinmarket.error;

import com.pumpkinmarket.error.code.ErrorCode;
import lombok.Getter;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.List;

@Getter
public class HttpErrorResponse {
    private final int httpStatus;
    private final String message;
    private final String errorCode;
    private final List<FieldError> errors;

    protected HttpErrorResponse(final ErrorCode errorCode) {
        this.httpStatus = errorCode.getHttpStatus();
        this.message = errorCode.getMessage();
        this.errorCode = errorCode.getErrorCode();
        this.errors = Collections.emptyList();
    }

    protected HttpErrorResponse(final ErrorCode errorCode, final List<FieldError> errors) {
        this.httpStatus = errorCode.getHttpStatus();
        this.message = errorCode.getMessage();
        this.errorCode = errorCode.getErrorCode();
        this.errors = errors;
    }

    protected HttpErrorResponse(final ErrorCode errorCode, final String errorMessage) {
        this.httpStatus = errorCode.getHttpStatus();
        this.message = errorMessage;
        this.errorCode = errorCode.getErrorCode();
        this.errors = Collections.emptyList();
    }

    public static HttpErrorResponse of(ErrorCode errorCode) {
        return new HttpErrorResponse(errorCode);
    }

    public static HttpErrorResponse of(ErrorCode errorCode, String errorMessage) {
        return new HttpErrorResponse(errorCode, errorMessage);
    }

    public static HttpErrorResponse of(ErrorCode errorCode, final BindingResult bindingResult) {
        return new HttpErrorResponse(errorCode, FieldError.of(bindingResult));
    }

    @Getter
    public static class FieldError {
        private final String field;
        private final String value;
        private final String reason;

        protected FieldError(final String field, final String value, final String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        private static List<FieldError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .toList();
        }
    }
}
