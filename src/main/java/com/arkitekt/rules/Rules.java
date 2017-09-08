package com.arkitekt.rules;


import com.arkitekt.domain.TacticItemType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rules {
    public static Map<String, List<RuleEntry>> TAKES_DAMAGE_FROM = new HashMap<>();
    public static Map<String, Integer> POWER_BONUS = new HashMap<>();
    public static Map<String, TacticItemType> TRIGGERS = new HashMap<>();
    static {
        try {
            load("./assets/rules.txt");
        } catch (Exception e) {
            System.out.println("failed to load rules");
        }
    }

    private static void load(String file) throws Exception{
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        while (line != null) {
            parseLine(line);
            line = reader.readLine();
        }
        reader.close();
    }

    private static void parseLine(String line) {
        String tokens[] = line.split(" ");
        String key = tokens[0];
        // %s takes damage from %s %d
        if (line.contains("takes damage")) {
            String from = tokens[4];
            Integer value = Integer.valueOf(tokens[5]);
            RuleEntry ruleEntry = new RuleEntry(value, from);
            if (!TAKES_DAMAGE_FROM.containsKey(key)) {
                TAKES_DAMAGE_FROM.put(key, new ArrayList<>());
            }
            TAKES_DAMAGE_FROM.get(key).add(ruleEntry);
        }
        // %s gives power bonus %d
        if (line.contains("power bonus")) {
            Integer value = Integer.valueOf(tokens[4]);
            POWER_BONUS.put(key, value);
        }
        // %s triggers %s
        if (line.contains("triggers")) {
            String value = tokens[2].toUpperCase();
            TRIGGERS.put(key, TacticItemType.valueOf(value));
        }
    }
}
