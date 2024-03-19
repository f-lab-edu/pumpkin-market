package com.pumpkinmarket.domain.user.api;

import com.pumpkinmarket.domain.user.application.UserService;
import com.pumpkinmarket.domain.user.dto.UserSignInDto;
import com.pumpkinmarket.domain.user.dto.UserSignupDto;
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
public class UserController {
    private final UserService userService;

    @PostMapping()
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
}
