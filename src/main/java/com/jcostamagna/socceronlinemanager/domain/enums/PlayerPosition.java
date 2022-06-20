package com.jcostamagna.socceronlinemanager.domain.enums;

public enum PlayerPosition {
    GOALKEEPER("Goalkeeper", 3),
    DEFENDER("Defender", 6),
    MIDFIELDER("Midfielder", 6),
    ATTACKER("Attacker", 5);

    private final String position;
    private final int initialQuantity;

    PlayerPosition(String position, int initialQuantity) {
        this.position = position;
        this.initialQuantity = initialQuantity;
    }

    @Override
    public String toString() {
        return position;
    }

    public int getQuantity() {
        return initialQuantity;
    }
}
