package com.arkitekt.domain;


import com.arkitekt.domain.master.Master;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class State {

    private static final int artBonus = 8;
    private static final int itemBonus = 3;
    private static final int runeBonus = 2;

    public List<Master> masters = new ArrayList<>();
    public String toString() {
        String s = "";
        int total = masters.stream().mapToInt(this::score).sum();
        List<Master> list = masters.stream().sorted((a, b) -> score(b) - score(a)).collect(toList());
        for (Master m : list) {
            s += m.name + "\n";
//            s += String.format("%d +%d\n", m.energy, m.income);
//            s += "at: " + m.atBonus + "\n";
//            if (!m.artifacts.isEmpty()) s += m.artifacts.toString() + "\n";
//            if (m.items > 0) s += "items: " + m.items + "\n";
//            if (m.runes > 0) s += "runes: " + m.runes + "\n";
            s += String.format("%.2f %.2f%% %s%d\n", score(m)/100.0, score(m)*100.0/total, (score(m)*100.0/total-10) > 0 ? "+" : "", (int)(score(m)*100.0/total+0.49)-10);
//            s += "\n";
        }
        return s;
    }

    private int score(Master m) {
        int gameLen = 16;
        double score = m.energy + m.income*gameLen;
        score += m.atBonus * 0.5 * gameLen;
        score -= 7*gameLen;
        score += artBonus * m.artifacts.size();
        score += itemBonus * m.items;
        score += runeBonus * m.runes;
        return (int)(score*100);
    }
}
