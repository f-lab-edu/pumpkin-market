package com.pumpkinmarket.domain.user.api;

import com.pumpkinmarket.annotations.AuthUser;
import com.pumpkinmarket.constants.UserTokenClaim;
import com.pumpkinmarket.domain.user.application.UserService;
import com.pumpkinmarket.domain.user.dto.UserDetailDto;
import com.pumpkinmarket.domain.user.dto.UserSignInDto;
import com.pumpkinmarket.domain.user.dto.UserSignupDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tags(@Tag(name = "User"))
public final class UserController {
    private final UserService userService;

    @PostMapping("sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(
        @Valid() @RequestBody UserSignupDto.UserSignupReq requestBody
    ) {
        this.userService.createUser(requestBody);
    }

    @PostMapping("sign-in")
    @ResponseStatus(HttpStatus.OK)
    public UserSignInDto.UserSignInRes signInUser(
            @Valid() @RequestBody UserSignInDto.UserSignInReq requestBody
    ) {
        return this.userService.signIn(requestBody.email(), requestBody.password());
    }

    @GetMapping("me")
    @AuthUser
    @SecurityRequirement(name = "Authorization-HTTP")
    @ResponseStatus(HttpStatus.OK)
    public UserDetailDto.UserDetailRes getMyDetail(
            UserTokenClaim user
    ) {
        System.out.println("user" + user);
        System.out.println("user" + user.id());
        return null;
    }
}
