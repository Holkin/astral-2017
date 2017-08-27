package com.arkitekt.battle;


import com.arkitekt.domain.Tactic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.arkitekt.names.Const.*;

public class UnitResolver {
    private static final Map<String, String> takesDamageFrom = new HashMap<>();
    static {
        takesDamageFrom.put(INFANTRY, ARCHERS);
        takesDamageFrom.put(ARCHERS, CAVALRY);
        takesDamageFrom.put(CAVALRY, INFANTRY);
    }

    public static BattleRecord resolve(Tactic myTactic, List<Tactic> others) {
        String myUnit = myTactic.getUnit().getName();
        long score = others.stream()
                .map(Tactic::getUnit)
                .filter(u -> u.getName().equals(takesDamageFrom.get(myUnit)))
                .count() * -2;
        score += others.stream()
                .filter(t -> t.getHero().getName().equals(COMMANDER))
                .filter(t -> t.getUnit().getName().equals(takesDamageFrom.get(myUnit)))
                .count() * -2;
        String description = myUnit + " " + score;
        return new BattleRecord(score, description);
    }
}
