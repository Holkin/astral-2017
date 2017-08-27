package com.arkitekt.battle;


import com.arkitekt.domain.Tactic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.arkitekt.names.Const.*;

public class HeroResolver {
    private static final Map<String, List<String>> takesDamageFrom = new HashMap<>();

    static {
        takesDamageFrom.put(WARRIOR, Arrays.asList(SCOUT));
        takesDamageFrom.put(SCOUT, Arrays.asList(COMMANDER));
        takesDamageFrom.put(COMMANDER, Arrays.asList(WARRIOR, WIZARD));
        takesDamageFrom.put(WIZARD, Arrays.asList(WARRIOR, SCOUT));
    }

    public static BattleRecord resolve(Tactic myTactic, List<Tactic> others) {
        String myHero = myTactic.getHero().getName();
        long score = others.stream()
                .map(Tactic::getHero)
                .filter(h -> {
                    List<String> list = takesDamageFrom.get(myHero);
                    return !list.isEmpty() && list.contains(h.getName());
                })
                .count() * -2;
        String description = myHero + " " + score;
        return new BattleRecord(score, description);
    }
}
