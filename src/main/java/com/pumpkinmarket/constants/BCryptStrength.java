package com.pumpkinmarket.constants;

public enum BCryptStrength {
    LOW(4),
    MEDIUM(12),
    HIGH(31);

    private final int strength;

    BCryptStrength(int strength) {
        this.strength = strength;
    }

    public int getStrength() {
        return this.strength;
    }
}
