package com.arkitekt.demo;

import com.arkitekt.battle.BattleResolver;
import com.arkitekt.domain.Attack;
import com.arkitekt.domain.Tactic;
import com.arkitekt.factory.TacticFactory;
import com.arkitekt.util.TextFileLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

//@Component
public class BattleDemoRunner implements CommandLineRunner {

    @Autowired
    BattleResolver resolver;

    @Override
    public void run(String... strings) throws Exception {
        List<String> lines = TextFileLoader.load("./assets/battles.txt");
        Map<String, List<Attack>> battles = new HashMap<>();
        List<Attack> currentBattle = new ArrayList<>();
        for (String line : lines) {
            String[] tokens = line.split(" ");
            switch (tokens[0]) {
                case "battle":
                    currentBattle = new ArrayList<>();
                    battles.put(line.substring("battle ".length()), currentBattle);
                    break;
                case "army":
                    String combatant = tokens[1];
                    int energy = Integer.valueOf(tokens[2]);
                    String unit = tokens[3];
                    String magic = tokens[4];
                    String hero = tokens[5];
                    String rune = tokens.length > 6 && !tokens[6].contains("=")
                            ? tokens[6] : null;
                    LinkedHashMap<String, Integer> bonuses = null;
                    if (tokens[tokens.length - 1].contains("=")) {
                        bonuses = new LinkedHashMap<>();
                        String[] params = tokens[tokens.length - 1].split(",");
                        for (String param : params) {
                            String[] val = param.split("=");
                            bonuses.put(val[0], Integer.valueOf(val[1]));
                        }
                    }
                    Tactic tactic = TacticFactory.tactic(unit, magic, hero, rune);
                    currentBattle.add(new Attack(combatant, tactic, energy, bonuses));
            }
        }

        List<String> report = new ArrayList<>();

        battles.entrySet().stream().forEach(b -> {
            report.add(b.getKey());
            resolver.resolve(b.getValue()).stream().forEach(report::add);
            report.add("");
        });

        report.stream().forEach(System.out::println);
        TextFileLoader.unload("./report.txt", report);
    }
}
