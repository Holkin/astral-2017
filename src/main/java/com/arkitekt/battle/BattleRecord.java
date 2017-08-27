package com.arkitekt.battle;


public class BattleRecord {
    private final long score;
    private final String description;

    public BattleRecord(long score, String description) {
        this.score = score;
        this.description = description;
    }

    public long getScore() {
        return score;
    }

    public String getDescription() {
        return description;
    }
}
