package com.pumpkinmarket.config;

import com.pumpkinmarket.components.DecoderTokenProvider;
import com.pumpkinmarket.components.TokenProvider;
import com.pumpkinmarket.interceptors.UserAuthInterceptor;
import com.pumpkinmarket.resolvers.UserArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final DecoderTokenProvider decoderTokenProvider;

    @Autowired
    public WebMvcConfig(DecoderTokenProvider decoderTokenProvider) {
        this.decoderTokenProvider = decoderTokenProvider;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserAuthInterceptor(decoderTokenProvider))
                .addPathPatterns("/api/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserArgumentResolver());
    }
}