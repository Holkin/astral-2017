package com.arkitekt.demo;

import com.arkitekt.battle.BattleResolver;
import com.arkitekt.domain.Attack;
import com.arkitekt.domain.Tactic;
import com.arkitekt.factory.TacticFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.arkitekt.names.Const.*;

//@Component
public class BattleDemoRunner implements CommandLineRunner {

    @Autowired
    BattleResolver resolver;

    @Override
    public void run(String... strings) throws Exception {
        List<Attack> battle = new ArrayList<>();

        Tactic t = TacticFactory.tactic(INFANTRY, ELEMENTAL, WIZARD, null);
        battle.add(new Attack("Local", t, 3, 0));
        t = TacticFactory.tactic(ARCHERS, WIZARDRY, WARRIOR, ELD);
        battle.add(new Attack("Daardus", t, 6, 0));
        t = TacticFactory.tactic(CAVALRY, WIZARDRY, SCOUT, ITH);
        battle.add(new Attack("Sinitar", t, 6, 0));

        System.out.println("\nBattle 1");
        resolver.resolve(battle);

        battle.clear();

        t = TacticFactory.tactic(INFANTRY, SORCERY, WIZARD, null);
        battle.add(new Attack("Local", t, 3, 0));
        t = TacticFactory.tactic(INFANTRY, SORCERY, WARRIOR, TAL);
        battle.add(new Attack("LeHunter", t, 5, 0));

        System.out.println("\nBattle 2");
        resolver.resolve(battle);

        battle.clear();

        t = TacticFactory.tactic(CAVALRY, ELEMENTAL, COMMANDER, null);
        battle.add(new Attack("Local", t, 0, 0));
        t = TacticFactory.tactic(CAVALRY, WIZARDRY, COMMANDER, null);
        battle.add(new Attack("ttt", t, 6, 0));

        System.out.println("\nBattle 3");
        resolver.resolve(battle);
        System.out.println();
    }
}
