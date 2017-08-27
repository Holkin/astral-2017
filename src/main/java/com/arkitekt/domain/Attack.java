package com.arkitekt.domain;


public class Attack {
    private final String attacker;
    private final Tactic tactic;
    private final int energy;
    private final int attackBonus;

    public Attack(String attacker, Tactic tactic, int energy, int attackBonus) {
        this.attacker = attacker;
        this.tactic = tactic;
        this.energy = energy;
        this.attackBonus = attackBonus;
    }

    public String getAttacker() {
        return attacker;
    }

    public Tactic getTactic() {
        return tactic;
    }

    public int getEnergy() {
        return energy;
    }

    public int getAttackBonus() {
        return attackBonus;
    }
}
