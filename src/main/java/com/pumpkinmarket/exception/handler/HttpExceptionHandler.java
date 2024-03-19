package com.pumpkinmarket.exception.handler;

import com.pumpkinmarket.error.HttpErrorResponse;
import com.pumpkinmarket.error.code.CommonErrorCode;
import com.pumpkinmarket.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.Collection;

@RestControllerAdvice
@Slf4j
public class HttpExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<HttpErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final HttpErrorResponse response = HttpErrorResponse.of(
                CommonErrorCode.VALIDATION_ERROR,
                e.getBindingResult()
        );

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getHttpStatus()));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    protected ResponseEntity<HttpErrorResponse> handlerMethodValidationException(HandlerMethodValidationException e) {
        final HttpErrorResponse response = HttpErrorResponse.of(
                CommonErrorCode.VALIDATION_ERROR,
                String.join(",", e.getAllValidationResults().stream().map(ParameterValidationResult::getResolvableErrors)
                        .flatMap(Collection::stream)
                        .map(MessageSourceResolvable::getDefaultMessage)
                        .toList())
        );

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getHttpStatus()));
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<HttpErrorResponse> handleBusinessException(BusinessException e) {
        final HttpErrorResponse response = HttpErrorResponse.of(e.getErrorCode(), e.getMessage());

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getHttpStatus()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<HttpErrorResponse> handleException(Exception e) {
        log.error("handleException", e);

        final HttpErrorResponse response = HttpErrorResponse.of(CommonErrorCode.UNKNOWN_ERROR, "Internal Server Error");

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getHttpStatus()));
    }
}
