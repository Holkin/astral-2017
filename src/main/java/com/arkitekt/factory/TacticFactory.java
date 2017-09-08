package com.arkitekt.factory;


import com.arkitekt.domain.*;
import com.arkitekt.util.TextFileLoader;

import java.util.ArrayList;
import java.util.List;

public class TacticFactory {

    private static final List<String> HEROES = new ArrayList<>();
    private static final List<String> MAGIC = new ArrayList<>();
    private static final List<String> UNITS = new ArrayList<>();
    private static final List<String> RUNES = new ArrayList<>();

    static {
        List<String> lines = TextFileLoader.load("./assets/names.txt");
        lines.stream().forEach(line -> {
            String[] tokens = line.split(" ");
            switch (tokens[0]) {
                case "unit":
                    UNITS.add(tokens[1]);
                    break;
                case "magic":
                    MAGIC.add(tokens[1]);
                    break;
                case "hero":
                    HEROES.add(tokens[1]);
                    break;
                case "rune":
                    RUNES.add(tokens[1]);
                    break;
            }
        });
    }

    public static Tactic tactic(String unit, String magic, String hero, String rune) {
        if (!UNITS.contains(unit)) {
            throw new RuntimeException("no such unit " + unit);
        }
        if (!MAGIC.contains(magic)) {
            throw new RuntimeException("no such magic " + magic);
        }
        if (!HEROES.contains(hero)) {
            throw new RuntimeException("no such hero " + hero);
        }
        return tacticSafe(unit, magic, hero, rune);
    }

    public static Tactic tacticSafe(String unit, String magic, String hero, String rune) {
        return new Tactic(
                new Unit(unit), new Magic(magic), new Hero(hero), new Rune(rune)
        );
    }
}
