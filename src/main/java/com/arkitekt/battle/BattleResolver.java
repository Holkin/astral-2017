package com.arkitekt.battle;


import com.arkitekt.domain.Attack;
import com.arkitekt.domain.Tactic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class BattleResolver {

    @Autowired
    TacticResolver tacticResolver;

    public void resolve(List<Attack> attacks) {
        List<Tactic> allTactics = attacks.stream().map(Attack::getTactic).collect(Collectors.toList());

        Map<String, Tactic> ownTactic = new HashMap<>();
        Map<String, List<Tactic>> otherTactics = new HashMap<>();

        for (Attack a : attacks) {
            ownTactic.put(a.getAttacker(), a.getTactic());
            List<Tactic> other = new ArrayList<>(allTactics);
            other.remove(a.getTactic());
            otherTactics.put(a.getAttacker(), other);
        }

        for (Attack a : attacks) {
            String name = a.getAttacker();
            BattleRecord record = tacticResolver.resolve(ownTactic.get(name), otherTactics.get(name));
            long score = a.getEnergy() + a.getAttackBonus() + record.getScore();

            String report = name + " " + a.getEnergy();
            report += record.getDescription();
            report += " = " + score;

            System.out.println(report);
        }
    }
}
