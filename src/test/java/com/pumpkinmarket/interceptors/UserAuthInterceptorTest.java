package com.pumpkinmarket.interceptors;

import com.pumpkinmarket.annotations.AuthUser;
import com.pumpkinmarket.components.DecoderTokenProvider;
import com.pumpkinmarket.constants.AttributeRequestScopeKey;
import com.pumpkinmarket.constants.UserTokenClaim;
import com.pumpkinmarket.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserAuthInterceptorTest {
    @Mock
    private HttpServletRequest httpServletRequestMock;
    @Mock
    private HttpServletResponse httpServletResponseMock;
    @Mock
    private DecoderTokenProvider decoderTokenProvider;
    UserAuthInterceptor userAuthInterceptor;
    @Mock
    HandlerMethod handlerMethod;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userAuthInterceptor = new UserAuthInterceptor(decoderTokenProvider);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(httpServletRequestMock));
    }

    @Test
    public void 권한이_필요하면서_메소드에_AuthUser가_있는경우() {
        final var user = new UserTokenClaim(1L);

        given(handlerMethod.getMethod()).willReturn(mock(Method.class));
        given(handlerMethod.getMethod().isAnnotationPresent(AuthUser.class))
                .willReturn(true);
        given(httpServletRequestMock.getHeader(HttpHeaders.AUTHORIZATION))
                .willReturn("bearer jwt_token");
        given(decoderTokenProvider.validateToken("jwt_token"))
                .willReturn(true);
        given(decoderTokenProvider.decodeToken("jwt_token", UserTokenClaim.class))
                .willReturn(new UserTokenClaim(1L));

        final var isSuccess
            = userAuthInterceptor.preHandle(httpServletRequestMock, httpServletResponseMock, handlerMethod);

        assertTrue(isSuccess);
        verify(httpServletRequestMock, times(1)).setAttribute(
                AttributeRequestScopeKey.USER.getKey(), user
        );
    }

//    @Test
//    public void 권한이_필요하면서_클래스에_AuthUser가_있는경우() {
//        final var user = new UserTokenClaim(1L);
//
//        given(handlerMethod.getBeanType()).willReturn((Class) MockController.class);
//        given(handlerMethod.getBeanType().isAnnotationPresent(AuthUser.class))
//                .willReturn(true);
//        given(httpServletRequestMock.getHeader(HttpHeaders.AUTHORIZATION))
//                .willReturn("bearer jwt_token");
//        given(decoderTokenProvider.validateToken("jwt_token"))
//                .willReturn(true);
//        given(decoderTokenProvider.decodeToken("jwt_token", UserTokenClaim.class))
//                .willReturn(user);
//
//        final var isSuccess
//            = userAuthInterceptor.preHandle(httpServletRequestMock, httpServletResponseMock, handlerMethod);
//
//        assertTrue(isSuccess);
//        verify(httpServletRequestMock, times(1)).setAttribute(
//                AttributeRequestScopeKey.USER.getKey(), user
//        );
//    }

    @Test
    public void 토큰이_없는데_AuthUser가_지정된경우() {
        given(handlerMethod.getMethod()).willReturn(mock(Method.class));
        given(handlerMethod.getMethod().isAnnotationPresent(AuthUser.class))
                .willReturn(true);

        assertThrows(BusinessException.class, () -> {
            userAuthInterceptor.preHandle(httpServletRequestMock, httpServletResponseMock, handlerMethod);
        });
    }
}

class MockController {

}
