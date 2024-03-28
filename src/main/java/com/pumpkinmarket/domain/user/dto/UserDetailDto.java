package com.pumpkinmarket.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public class UserDetailDto {
    public record UserDetailRes(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        Long id,
        @Schema(description = "닉네임", requiredMode = Schema.RequiredMode.REQUIRED)
        String nickname,
        @Schema(description = "핸드폰 번호", requiredMode = Schema.RequiredMode.REQUIRED)
        String phone
    ) {
    }
}
