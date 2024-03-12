package com.pumpkinmarket.components;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pumpkinmarket.constants.IJwtClaim;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider implements TokenProvider {
    private final SecretKey secretKey;

    @Autowired
    public JwtTokenProvider(@Value("${jwt.secretKey}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String createToken(IJwtClaim jwtClaim, long expiredMs) {
        return Jwts
                .builder()
                .signWith(this.secretKey)
                .claims(jwtClaim.toMap())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + expiredMs))
                .compact();
    }

    public <T extends IJwtClaim> T decodeJwt(
            String jwtToken,
            Class<T> tClass
    ) {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            final Claims claims = Jwts.parser()
                    .verifyWith(this.secretKey)
                    .build()
                    .parseSignedClaims(jwtToken)
                    .getPayload();

            return objectMapper.convertValue(claims, tClass);
        } catch (ExpiredJwtException exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public boolean validateToken(String jwtToken) {
        try {
            Jwts.parser()
                    .verifyWith(this.secretKey)
                    .build()
                    .parseSignedClaims(jwtToken);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
