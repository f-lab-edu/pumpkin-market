package com.pumpkinmarket.config;

import com.pumpkinmarket.constants.UserTokenClaim;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Authorization-HTTP",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfig {
    static {
        SpringDocUtils.getConfig().addRequestWrapperToIgnore(UserTokenClaim.class);
    }
}
