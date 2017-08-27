package com.arkitekt.battle;


import com.arkitekt.domain.Rune;
import com.arkitekt.domain.Tactic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.arkitekt.names.Const.*;

public class RuneResolver {
    private static final Map<String, Integer> powerBonus = new HashMap<>();
    private static final Map<String, Entry> takesDamageFrom = new HashMap<>();

    static {
        powerBonus.put(TAL, 2);
        powerBonus.put(THUL, 2);
        powerBonus.put(RAL, 2);
        powerBonus.put(ORT, 2);
        powerBonus.put(ETH, 2);
        powerBonus.put(ITH, 2);
        powerBonus.put(EL, 1);
        powerBonus.put(ELD, 1);
        powerBonus.put(TIR, 1);

        powerBonus.put(EL + "." + INFANTRY, 1);
        powerBonus.put(ELD + "." + ARCHERS, 1);
        powerBonus.put(TIR + "." + CAVALRY, 1);

        takesDamageFrom.put(TAL, entry(ITH, 2));
        takesDamageFrom.put(THUL, entry(TAL, 2));
        takesDamageFrom.put(RAL, entry(THUL, 2));
        takesDamageFrom.put(ORT, entry(RAL, 2));
        takesDamageFrom.put(ETH, entry(ORT, 2));
        takesDamageFrom.put(ITH, entry(ETH, 2));
        takesDamageFrom.put(EL, entry(ELD, 1));
        takesDamageFrom.put(ELD, entry(TIR, 1));
        takesDamageFrom.put(TIR, entry(EL, 1));
    }

    public static BattleRecord resolve(Tactic myTactic, List<Tactic> others) {
        List<String> otherRunes = others.stream()
                .filter(t -> t.getRune() != null)
                .map(Tactic::getRune)
                .map(Rune::getName)
                .collect(Collectors.toList());
        String myRune = myTactic.getRune().getName();
        if (myRune == null) {
            return new BattleRecord(0, "");
        }

        long score = powerBonus.get(myRune);
        Integer bonus = powerBonus.get(myRune + "." + myTactic.getUnit().getName());
        score += bonus == null ? 0 : bonus;

        score -= otherRunes.stream()
                .mapToInt(r -> {
                    Entry entry = takesDamageFrom.get(myRune);
                    if (entry == null || !entry.getKey().equals(myRune)) {
                        return 0;
                    }
                    return entry.getValue();
                })
                .sum();

        String description = myRune + " " + score;
        return new BattleRecord(score, description);
    }

    public static Entry entry(String key, int value) {
        return new Entry(key, value);
    }

    private static class Entry {
        private String key;
        private int value;

        public Entry(String key, int value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public int getValue() {
            return value;
        }
    }
}
