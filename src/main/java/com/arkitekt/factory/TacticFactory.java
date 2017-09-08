package com.arkitekt.factory;


import com.arkitekt.domain.*;

import java.util.Arrays;

import static com.arkitekt.names.Const.*;

public class TacticFactory {

    private static final String HEROES[] = {
            WARRIOR, SCOUT, COMMANDER, WIZARD
    };
    private static final String MAGIC[] = {
            WIZARDRY, SORCERY, ELEMENTAL
    };
    private static final String UNITS[] = {
            INFANTRY, ARCHERS, CAVALRY
    };
    private static final String RUNES[] = {
            TAL, THUL, RAL, ORT, ETH, ITH, EL, ELD, TIR
    };

    public static Tactic tactic(String unit, String magic, String hero, String rune) {
        if (!Arrays.asList(UNITS).contains(unit)) {
            throw new RuntimeException("no such unit " + unit);
        }
        if (!Arrays.asList(MAGIC).contains(magic)) {
            throw new RuntimeException("no such magic " + magic);
        }
        if (!Arrays.asList(HEROES).contains(hero)) {
            throw new RuntimeException("no such hero " + hero);
        }
        return tacticSafe(unit, magic, hero, rune);
    }

    public static Tactic tactic(int unit, int magic, int hero, String rune) {
        return tacticSafe(UNITS[unit - 1], MAGIC[magic - 1], HEROES[hero - 1], rune);
    }

    public static Tactic tacticSafe(String unit, String magic, String hero, String rune) {
        return new Tactic(
                new Unit(unit), new Magic(magic), new Hero(hero), new Rune(rune)
        );
    }
}
