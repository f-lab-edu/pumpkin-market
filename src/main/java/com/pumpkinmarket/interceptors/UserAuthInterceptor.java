package com.pumpkinmarket.interceptors;

import com.pumpkinmarket.annotations.AuthUser;
import com.pumpkinmarket.components.DecoderTokenProvider;
import com.pumpkinmarket.constants.AttributeRequestScopeKey;
import com.pumpkinmarket.constants.UserTokenClaim;
import com.pumpkinmarket.error.code.CommonErrorCode;
import com.pumpkinmarket.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

public class UserAuthInterceptor implements HandlerInterceptor {
    private final String tokenType = "bearer";
    @SuppressWarnings("FieldCanBeLocal")
    private final String accessTokenHeader = "authorization";
    private final DecoderTokenProvider decoderTokenProvider;

    public UserAuthInterceptor(DecoderTokenProvider decoderTokenProvider) {
        this.decoderTokenProvider = decoderTokenProvider;
    }

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler
    ) {
        final var handlerMethod = (HandlerMethod) handler;
        final var annotation = handlerMethod.getMethodAnnotation(AuthUser.class);

        if (annotation != null) {
            final var accessToken = Optional.ofNullable(request.getHeader(accessTokenHeader))
                    .map((header) -> header.substring(tokenType.length() + 1))
                    .orElseThrow(() -> BusinessException.of(CommonErrorCode.UNAUTHORIZED_ERROR));

            if (!this.decoderTokenProvider.validateToken(accessToken)) {
                throw BusinessException.of(CommonErrorCode.UNAUTHORIZED_ERROR);
            }

            final var user =
                    this.decoderTokenProvider.decodeToken(accessToken, UserTokenClaim.class);

            RequestContextHolder.currentRequestAttributes()
                    .setAttribute(AttributeRequestScopeKey.USER.getKey(), user, RequestAttributes.SCOPE_REQUEST);
        }

        return true;
    }
}
