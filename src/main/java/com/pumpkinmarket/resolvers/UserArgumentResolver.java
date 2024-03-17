package com.pumpkinmarket.resolvers;

import com.pumpkinmarket.constants.AttributeRequestScopeKey;
import com.pumpkinmarket.constants.UserTokenClaim;
import com.pumpkinmarket.error.code.CommonErrorCode;
import com.pumpkinmarket.exception.BusinessException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType() == UserTokenClaim.class;
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  @NonNull NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory
    ) {
        final var user = (UserTokenClaim) RequestContextHolder.currentRequestAttributes().getAttribute(
                AttributeRequestScopeKey.USER.getKey(), RequestAttributes.SCOPE_REQUEST
        );

        if (user == null) {
            log.info("UserTokenClaim null");
            throw BusinessException.of(CommonErrorCode.UNKNOWN_ERROR);
        }

        return user;
    }
}
