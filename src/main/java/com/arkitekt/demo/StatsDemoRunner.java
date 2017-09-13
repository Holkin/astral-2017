package com.arkitekt.demo;


import com.arkitekt.battle.TacticResolver;
import com.arkitekt.domain.Tactic;
import com.arkitekt.factory.TacticFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.arkitekt.factory.TacticFactory.CreationMode.Strategy.*;
import static com.arkitekt.factory.TacticFactory.CreationMode;
import static com.arkitekt.domain.TacticItemType.*;

@Component
public class StatsDemoRunner implements CommandLineRunner {
    @Autowired
    private TacticResolver resolver;
    @Override
    public void run(String... strings) throws Exception {
//        List<Tactic> hero = TacticFactory.getTactics(new CreationMode(ALL).withType(UNIT), new CreationMode(ALL).withType(MAGIC), new CreationMode(ALL).withType(HERO), new CreationMode(ALL).withType(RUNE));

        List<Tactic> hero = TacticFactory.getTactics(
                new CreationMode(VALUE).withType(UNIT).withValue("Archers"),
                new CreationMode(VALUE).withType(MAGIC).withValue("Sorcery"),
                new CreationMode(VALUE).withType(HERO).withValue("Scout"),
                new CreationMode(VALUE).withType(RUNE).withValue("Ith"));

        List<Tactic> villain = TacticFactory.getTactics(
                new CreationMode(ALL).withType(UNIT),
                new CreationMode(ALL).withType(MAGIC),
                new CreationMode(ALL).withType(HERO),
                new CreationMode(ALL).withType(RUNE));

        List<Tactic> thirdParty =
                TacticFactory.getTactics(new CreationMode(ALL).withType(UNIT),
                        new CreationMode(ALL).withType(MAGIC), new CreationMode(ALL).withType(HERO),
//                        new CreationMode(VALUE).withType(RUNE).withValue(null));
                        new CreationMode(ALL).withType(RUNE));
        int total = villain.size() * thirdParty.size();

        int heroBonus = 11 ;
        int villainBonus = 11+ 1;
        int thirdPartyBonus = 9;
        Map<Tactic, Integer> wins = new HashMap<>();
        hero.parallelStream().forEach(t -> {
            int ctr = 0;
            for (Tactic vt : villain) {
                for (Tactic nt : thirdParty) {
                    long hs = resolver.resolve(t, Arrays.asList(vt, nt)).getScore() + heroBonus;
                    long vs = resolver.resolve(vt, Arrays.asList(t, nt)).getScore() + villainBonus;
                    long ns = resolver.resolve(nt, Arrays.asList(vt, t)).getScore() + thirdPartyBonus;
                    if (hs > Math.max(vs, ns) || (ns == vs && vs > hs)) {
                        ctr++;
                    }
                }
            }
            System.out.println(".");
            wins.put(t, ctr);
        });

        wins.entrySet().stream().sorted((a,b) -> a.getValue() - b.getValue())
                .forEach(e -> System.out.println(e.getKey() + " wins = " + toPercentage(e.getValue(), total)));
        System.out.println();

        Tactic t = hero.get(0);
        int ctr = 0;
        for (Tactic vt : villain) {
            long hs = resolver.resolve(t, Arrays.asList(vt)).getScore();
            long vs = resolver.resolve(vt, Arrays.asList(t)).getScore();
            if (hs-vs >= 8) {
                ctr++;
                System.out.println(vt);
            }
        }
        System.out.println(ctr);
        System.out.println(villain.size());
        System.out.println(toPercentage(ctr, total/thirdParty.size()));

    }

    private String toPercentage(int val, int total) {
        return new BigDecimal(val / (total/100.0)).setScale(10, RoundingMode.HALF_UP) + "%";
    }
}
