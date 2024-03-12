package com.pumpkinmarket.components;

import com.pumpkinmarket.constants.IJwtClaim;

public interface TokenProvider {
    String createToken(IJwtClaim jwtClaim, long expiredMs);
    boolean validateToken(String jwtToken);
}
