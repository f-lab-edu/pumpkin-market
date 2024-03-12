package com.pumpkinmarket.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public class UserSignInDto {
    public record UserSignInReq(
        @Schema(description = "이메일", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotEmpty
        String email,
        @Schema(description = "비밀번호, 영문/숫자 조합 최소 4자 이상 20자 이하 ", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotEmpty
        String password
    ) {
    }

    public record UserSignInRes(
        @Schema
        String accessToken
    ) {

    }
}
