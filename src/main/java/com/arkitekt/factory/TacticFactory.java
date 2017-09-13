package com.arkitekt.factory;


import com.arkitekt.domain.*;
import com.arkitekt.util.TextFileLoader;

import java.util.*;

public class TacticFactory {

    private static final List<String> HEROES = new ArrayList<>();
    private static final List<String> MAGIC = new ArrayList<>();
    private static final List<String> UNITS = new ArrayList<>();
    private static final List<String> RUNES = new ArrayList<>();
    private static final Map<TacticItemType, List<String>> ITEMS = new HashMap<>();

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
        ITEMS.put(TacticItemType.UNIT, UNITS);
        ITEMS.put(TacticItemType.MAGIC, MAGIC);
        ITEMS.put(TacticItemType.HERO, HEROES);
        ITEMS.put(TacticItemType.RUNE, RUNES);
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

    public static List<Tactic> getTactics(CreationMode unitCm, CreationMode magicCm, CreationMode heroCm, CreationMode runeCm) {
        List<Tactic> result = new ArrayList<>();
        for (String unit : unitCm.listOptions()) {
            for (String magic : magicCm.listOptions()) {
                for (String hero : heroCm.listOptions()) {
                    for (String rune : runeCm.listOptions()) {
                        result.add(tactic(unit, magic, hero, rune));
                    }
                }
            }
        }
        return result;
    }

    public static class CreationMode {
        private Strategy strategy;
        private String value;
        private TacticItemType type;
        private Random r;

        public CreationMode(Strategy strategy) {
            this.strategy = strategy;
        }

        public CreationMode withValue(String value) {
            this.value = value;
            return this;
        }
        public CreationMode withType(TacticItemType type) {
            this.type = type;
            return this;
        }
        public CreationMode withRandom(Random random) {
            this.r = random;
            return this;
        }
        public List<String> listOptions() {
            switch (strategy) {
                case ALL :
                    return ITEMS.get(type);
                case RANDOM :
                    List<String> list = ITEMS.get(type);
                    return Collections.singletonList(list.get(r.nextInt(list.size())));
                default :
                    return Collections.singletonList(value);
            }

        }
        public enum Strategy {
            ALL, RANDOM, VALUE;
        }
    }
}
