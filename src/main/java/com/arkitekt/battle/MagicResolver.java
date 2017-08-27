package com.arkitekt.battle;


import com.arkitekt.domain.Tactic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.arkitekt.names.Const.*;

public class MagicResolver {
    private static final Map<String, String> takesDamageFrom = new HashMap<>();

    static {
        takesDamageFrom.put(WIZARDRY, ELEMENTAL);
        takesDamageFrom.put(SORCERY, WIZARDRY);
        takesDamageFrom.put(ELEMENTAL, SORCERY);
    }

    public static BattleRecord resolve(Tactic myTactic, List<Tactic> others) {
        String myMagic = myTactic.getMagic().getName();
        long score = others.stream()
                .map(Tactic::getMagic)
                .filter(m -> m.getName().equals(takesDamageFrom.get(myMagic)))
                .count() * -2;
        score += others.stream()
                .filter(t -> t.getHero().getName().equals(WIZARD))
                .filter(t -> t.getMagic().getName().equals(takesDamageFrom.get(myMagic)))
                .count() * -2;
        String description = myMagic + " " + score;
        return new BattleRecord(score, description);
    }
}
