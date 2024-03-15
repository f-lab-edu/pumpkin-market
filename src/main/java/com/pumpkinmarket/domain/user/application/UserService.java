package com.pumpkinmarket.domain.user.application;

import com.pumpkinmarket.components.TokenProvider;
import com.pumpkinmarket.constants.UserTokenClaim;
import com.pumpkinmarket.domain.user.domain.User;
import com.pumpkinmarket.domain.user.dto.UserSignInDto;
import com.pumpkinmarket.domain.user.dto.UserSignupDto;
import com.pumpkinmarket.domain.user.repository.UserRepository;
import com.pumpkinmarket.error.code.CommonErrorCode;
import com.pumpkinmarket.error.code.UserErrorCode;
import com.pumpkinmarket.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public Long createUser(UserSignupDto.UserSignupReq requestBody) {
        final var password = passwordEncoder.encode(requestBody.password());

        if (this.existsByEmail(requestBody.email())) {
            throw BusinessException.of(UserErrorCode.ALREADY_EXISTS_USER);
        }

        final var user = new User(
            requestBody.email(),
            password,
            requestBody.nickname(),
            requestBody.phone()
        );

        this.userRepository.save(user);

        return user.getId();
    }

    private boolean existsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public UserSignInDto.UserSignInRes signIn(String email, String password) {
        final var user = this.userRepository.findByEmail(email).orElseThrow(() ->
                BusinessException.of(CommonErrorCode.UNAUTHORIZED_ERROR));

        if (!this.passwordEncoder.matches(password, user.getPassword())) {
            throw BusinessException.of(CommonErrorCode.UNAUTHORIZED_ERROR);
        }

        return new UserSignInDto.UserSignInRes(
            this.tokenProvider.createToken(new UserTokenClaim(user.getId()))
        );
    }
}
