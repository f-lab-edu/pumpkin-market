package com.pumpkinmarket.domain.user.application;

import com.pumpkinmarket.components.JwtTokenProvider;
import com.pumpkinmarket.constants.UserJwtClaim;
import com.pumpkinmarket.domain.user.domain.User;
import com.pumpkinmarket.domain.user.dto.UserSignInDto;
import com.pumpkinmarket.domain.user.dto.UserSignupDto;
import com.pumpkinmarket.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Long createUser(UserSignupDto.UserSignupReq requestBody) {
        final var password = passwordEncoder.encode(requestBody.password());

        if (this.existsByEmail(requestBody.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다.");
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
                new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        if (!this.passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        return new UserSignInDto.UserSignInRes(
            this.jwtTokenProvider.createToken(new UserJwtClaim(user.getId()))
        );
    }
}
