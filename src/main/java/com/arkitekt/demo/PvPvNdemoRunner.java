package com.arkitekt.demo;

import com.arkitekt.battle.TacticResolver;
import com.arkitekt.domain.Tactic;
import com.arkitekt.factory.TacticFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Component
public class PvPvNdemoRunner implements CommandLineRunner {

    @Autowired
    TacticResolver resolver;

    @Override
    public void run(String... strings) throws Exception {
        int bonus = 7;
        int enemyBonus = 11;
        List<Tactic> another = TacticFactory.getAllTactics(true);
        List<Tactic> neutral = TacticFactory.getAllTactics(false);
        List<Tactic> tactics = TacticFactory.getAllTactics(0);

        int total = another.size() * neutral.size();
        Map<Tactic, Integer> wins = new HashMap<>();
        Map<Tactic, Integer> draws = new HashMap<>();
        Map<Tactic, Integer> loses = new HashMap<>();

        for (Tactic t : tactics) {
            int w = 0, d = 0, l = 0;
            for (Tactic a : another) {
                for (Tactic n : neutral) {
                    long me = resolver.resolve(t, Arrays.asList(a, n)).getScore() + bonus;
                    long nt = resolver.resolve(n, Arrays.asList(a, t)).getScore();
                    long an = resolver.resolve(a, Arrays.asList(t, n)).getScore() + enemyBonus;
                    if (me > nt && me > an) {
                        w++;
                    }
                    if (me == Math.max(me, Math.max(nt, an))) {
                        d++;
                    }
                    if (me < nt || me < an) {
                        l++;
                    }
                }
            }
            wins.put(t, w);
            draws.put(t, d);
            loses.put(t, l);
        }

        wins.entrySet().stream().sorted((a,b) -> a.getValue() - b.getValue())
                .forEach(e -> System.out.println(e.getKey() + " wins = " + toPercentage(e.getValue(), total)));
        System.out.println();
//        draws.entrySet().stream().sorted((a,b) -> a.getValue() - b.getValue())
//                .forEach(e -> System.out.println(e.getKey() + " draws = " + toPercentage(e.getValue(), total)));
//        System.out.println();
        loses.entrySet().stream().sorted((a,b) -> b.getValue() - a.getValue())
                .forEach(e -> System.out.println(e.getKey() + " no lose = " + toPercentage(total-e.getValue(),total)));
        System.out.println();
    }

    private String toPercentage(int val, int total) {
        return new BigDecimal(val / (total/100.0)).setScale(2, RoundingMode.HALF_UP) + "%";
    }
}
