package com.arkitekt.battle;


import com.arkitekt.domain.Tactic;
import com.arkitekt.domain.TacticItem;
import com.arkitekt.domain.TacticItemType;
import com.arkitekt.rules.RuleEntry;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.arkitekt.domain.TacticItemType.*;
import static com.arkitekt.rules.Rules.*;

@Component
public class TacticResolver {

    public BattleRecord resolve(Tactic myTactic, List<Tactic> others) {
        Map<TacticItemType, BattleRecord> records = new HashMap<>();

        Arrays.stream(TacticItemType.values())
                .forEach(t -> records.put(t, resolve(t, myTactic, others)));


        long score = records.values().stream().mapToLong(BattleRecord::getScore).sum();
        String report = String.format(" + (%s, %s, %s)",
                records.get(UNIT).getDescription(), records.get(MAGIC).getDescription(), records.get(HERO).getDescription());
        if (records.get(RUNE).getScore() != 0) {
            report += " + " + records.get(RUNE).getDescription();
        }

        return new BattleRecord(score, report);
    }

    private BattleRecord resolve(TacticItemType type, Tactic myTactic, List<Tactic> others) {
        String myItem = myTactic.getTacticMap().get(type).getName();
        long score = POWER_BONUS.containsKey(myItem) ? POWER_BONUS.get(myItem) : 0;
        if (type == RUNE) { // additional rule for runes
            String key = myItem + "." + myTactic.getTacticMap().get(UNIT).getName();
            score += POWER_BONUS.containsKey(key) ? POWER_BONUS.get(key) : 0;
        }

        Map<String, Long> triggers = others.stream()
                .flatMap(t -> t.getTacticMap().values().stream())
                .filter(it -> type.equals(TRIGGERS.get(it.getName())))
                .collect(Collectors.groupingBy(TacticItem::getName, Collectors.counting()));

        score -= others.stream()
                .mapToLong(enemyTactic -> {
                    String enemyItem = enemyTactic.getTacticMap().get(type).getName();
                    Set<String> enemyTacticItemNames = enemyTactic.getTacticMap().values().stream()
                            .map(TacticItem::getName)
                            .collect(Collectors.toSet());
                    long mod = triggers.entrySet().stream()
                            .filter(entry -> enemyTacticItemNames.contains(entry.getKey()))
                            .mapToLong(Map.Entry::getValue)
                            .sum();

                    List<RuleEntry> ruleEntries = TAKES_DAMAGE_FROM.get(myItem);
                    if (!CollectionUtils.isEmpty(ruleEntries)) {
                        return ruleEntries.stream()
                                .filter(e -> e.getKey().equals(enemyItem))
                                .mapToLong(e -> e.getValue() * (mod + 1))
                                .sum();
                    }
                    return 0;
                }).sum();
        return new BattleRecord(score, String.format("%s %d", myItem, score));
    }
}
