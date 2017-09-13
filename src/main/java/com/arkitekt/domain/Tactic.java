package com.arkitekt.domain;


import java.util.HashMap;
import java.util.Map;

import static com.arkitekt.domain.TacticItemType.*;

public class Tactic {
    private final Map<TacticItemType, TacticItem> map = new HashMap<>();

    public Tactic(Unit unit, Magic magic, Hero hero, Rune rune) {
        map.put(UNIT, unit);
        map.put(MAGIC, magic);
        map.put(HERO, hero);
        map.put(RUNE, rune);
    }

    public Map<TacticItemType, TacticItem> getTacticMap() {
        return map;
    }

    public Unit getUnit() {
        return (Unit) map.get(UNIT);
    }

    public Magic getMagic() {
        return (Magic) map.get(MAGIC);
    }

    public Hero getHero() {
        return (Hero) map.get(HERO);
    }

    public Rune getRune() {
        return (Rune) map.get(RUNE);
    }

    @Override
    public String toString() {
        return "Tactic{" +
                "unit=" + getUnit().getName() +
                ", magic=" + getMagic().getName() +
                ", hero=" + getHero().getName() +
                ", rune=" + (getRune() == null ? "" : getRune().getName()) +
                '}';
    }
}
