package com.pumpkinmarket.components;

import com.pumpkinmarket.constants.ITokenClaim;

import java.time.Duration;

public interface TokenProvider {
    String createToken(ITokenClaim jwtClaim, long expiredMs);
    boolean validateToken(String jwtToken);
    default String createToken(ITokenClaim jwtClaim) {
        return this.createToken(jwtClaim, Duration.ofHours(1).toMillis());
    }
}
