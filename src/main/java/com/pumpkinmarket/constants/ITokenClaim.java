package com.pumpkinmarket.constants;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.Map;

public interface ITokenClaim extends Serializable {
    @SuppressWarnings("unchecked")
    default Map<String, ?> toMap() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(this, Map.class);
    }
}
