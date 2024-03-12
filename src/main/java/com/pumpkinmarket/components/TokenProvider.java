package com.pumpkinmarket.components;

import com.pumpkinmarket.constants.IJwtClaim;

import java.time.Duration;

public interface TokenProvider {
    String createToken(IJwtClaim jwtClaim, long expiredMs);
    boolean validateToken(String jwtToken);
    default String createToken(IJwtClaim jwtClaim) {
        return this.createToken(jwtClaim, Duration.ofHours(1).toMillis());
    }
}
