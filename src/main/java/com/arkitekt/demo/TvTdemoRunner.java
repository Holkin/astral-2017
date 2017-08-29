package com.arkitekt.demo;


import com.arkitekt.battle.BattleRecord;
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

@Component
public class TvTdemoRunner implements CommandLineRunner {

    @Autowired
    TacticResolver resolver;

    @Override
    public void run(String... strings) throws Exception {

        double[] arr = {20.37, 39.31, 42.3, 61.92, 64.16, 77.99, 79.51, 89.61, 90.76, 96.5, 96.92, 99, 99.17, 99.86, 99.89, 100, 100};

        int bonus = 5;
        List<Tactic> tactics = TacticFactory.getAllTactics(false);
        List<Tactic> enemyTactics = TacticFactory.getAllTactics(true);
        int total = enemyTactics.size();

        Map<Tactic, Integer> wins = new HashMap<>();
        Map<Tactic, Integer> draws = new HashMap<>();
        Map<Tactic, Integer> loses = new HashMap<>();

        for (Tactic tactic : tactics) {
            int winCtr = 0, drawCtr = 0, lostCtr = 0;
            for (Tactic enemyTactic : enemyTactics) {
                BattleRecord r0 = resolver.resolve(tactic, Arrays.asList(enemyTactic));
                BattleRecord r1 = resolver.resolve(enemyTactic, Arrays.asList(tactic));
                long myScore = r0.getScore() + bonus;
                if (myScore > r1.getScore()) {
                    winCtr++;
                }
                if (myScore == r1.getScore()) {
                    drawCtr++;
                }
                if (myScore < r1.getScore()) {
                    lostCtr++;
                }
            }
            wins.put(tactic, winCtr);
            draws.put(tactic, drawCtr);
            loses.put(tactic, lostCtr);
            System.out.println(tactic + " wins = " + winCtr + ", draws = " + drawCtr);
        }

        wins.entrySet().stream().sorted((a,b) -> a.getValue() - b.getValue())
                .forEach(e -> System.out.println(e.getKey() + " wins = " + toPercentage(e.getValue(),total)));
        System.out.println();
//        draws.entrySet().stream().sorted((a,b) -> a.getValue() - b.getValue())
//                .forEach(e -> System.out.println(e.getKey() + " draws = " + toPercentage(e.getValue(),total)));
//        System.out.println();
//        loses.entrySet().stream().sorted((a,b) -> b.getValue() - a.getValue())
//                .forEach(e -> System.out.println(e.getKey() + " no lose = " + toPercentage(total-e.getValue(),total)));
//        System.out.println();

        int value =  10;
        for (int i=0; i<arr.length; i++) {
            System.out.println(""+i+" "+new BigDecimal((10*arr[i]/100-2-4)/(i+.0000001)).setScale(2, BigDecimal.ROUND_HALF_UP)+"");
        }
    }

    private String toPercentage(int val, int total) {
        return new BigDecimal(val / (total/100.0)).setScale(2, RoundingMode.HALF_UP) + "%";
    }


}
