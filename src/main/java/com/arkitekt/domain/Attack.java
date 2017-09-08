package com.arkitekt.domain;


import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class Attack {
    private final String attacker;
    private final Tactic tactic;
    private final int energy;
    private final LinkedHashMap<String, Integer> attackBonus;

    public Attack(String attacker, Tactic tactic, int energy, LinkedHashMap<String, Integer> attackBonus) {
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

    public int getTotalAttackBonus() {
        if (attackBonus == null) {
            return 0;
        }
        return attackBonus.values().stream().mapToInt(v -> v).sum();
    }

    public String getAttackBonusDescription() {
        if (attackBonus == null) {
            return "";
        }
        return attackBonus.keySet().stream().collect(Collectors.joining(", "));
    }
}
