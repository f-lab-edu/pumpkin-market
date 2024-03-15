package com.pumpkinmarket.config;

import com.pumpkinmarket.components.DecoderTokenProvider;
import com.pumpkinmarket.components.TokenProvider;
import com.pumpkinmarket.interceptors.UserAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
}