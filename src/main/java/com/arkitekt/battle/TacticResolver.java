package com.arkitekt.battle;


import com.arkitekt.domain.Tactic;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TacticResolver {
    public BattleRecord resolve(Tactic myTactic, List<Tactic> others) {
        BattleRecord[] subrecords = {
                UnitResolver.resolve(myTactic, others),
                MagicResolver.resolve(myTactic, others),
                HeroResolver.resolve(myTactic, others),
                RuneResolver.resolve(myTactic, others)
        };

        long score = Arrays.asList(subrecords).stream()
                .mapToLong(BattleRecord::getScore)
                .sum();

        Object[] d = Arrays.asList(subrecords).stream()
                .map(BattleRecord::getDescription)
                .collect(Collectors.toList())
                .toArray();

        String report = String.format(" + (%s, %s, %s)", d[0], d[1], d[2]);
        if (!"".equals(d[3])) {
            report += " + "  + d[3];
        }

        return new BattleRecord(score, report);
    }
}
