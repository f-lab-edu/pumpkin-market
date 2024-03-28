package com.pumpkinmarket.components;

import com.pumpkinmarket.constants.ITokenClaim;

public interface DecoderTokenProvider extends TokenProvider {
    <T extends ITokenClaim> T decodeToken(
            String token,
            Class<T> tClass
    );
}
