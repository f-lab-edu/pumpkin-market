package com.pumpkinmarket.constants;

import lombok.Getter;

@Getter
public enum AttributeRequestScopeKey {
    USER("user");

    private final String key;

    AttributeRequestScopeKey(String key) {
        this.key = key;
    }
}
