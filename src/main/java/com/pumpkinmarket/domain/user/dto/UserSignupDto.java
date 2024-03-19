package com.pumpkinmarket.domain.user.dto;

import com.pumpkinmarket.validation.annotations.IsPassword;
import com.pumpkinmarket.validation.annotations.IsPhone;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class UserSignupDto {
    public record UserSignupReq(
        @Schema(description = "이메일", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotEmpty
        @Email
        String email,
        @Schema(description = "비밀번호, 영문/숫자 조합 최소 4자 이상 20자 이하 ", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotEmpty
        @IsPassword(message = "비밀번호는 영문/숫자 조합 최소 4자 이상 20자 이하 이어야 합니다.")
        String password,
        @Schema(description = "닉네임", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotEmpty
        String nickname,
        @Schema(description = "핸드폰 번호", requiredMode = Schema.RequiredMode.REQUIRED)
        @IsPhone()
        String phone
    ) {
    }
}
