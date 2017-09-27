package com.arkitekt.io;


import com.arkitekt.domain.State;
import com.arkitekt.domain.master.Master;
import com.arkitekt.util.TextFileLoader;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class StateReader {
    public State load(String file) {
        State state = new State();
        Mode mode = Mode.TEXT;
        for (String line : TextFileLoader.load(file)) {
            switch (mode) {
                case MASTERS:
                    loadMaster(state, line);
                    break;
                default:
                    if (line.contains("Владыки")) {
                        mode = Mode.MASTERS;
                    }
            }

        }
        return state;
    }

    private void loadMaster(State state, String line) {
        if (line.contains("Владыка")) {
            addMaster(state, line);
            return;
        }
        if (state.masters.isEmpty()) {
            return;
        }
        Master current = state.masters.get(state.masters.size() - 1);
        if (line.contains("Энергия")) {
            current.energy = fromLine(line);
        }
        else if (line.contains("Доход")) {
            current.income += Arrays.stream(line.split(" "))
                    .filter(t -> t.contains("+"))
                    .map(String::trim).map(s -> s.replace(",", "").replace(".", ""))
                    .mapToInt(Integer::valueOf)
                    .sum();
        }
        else if (line.contains("Сила атаки")) {
            current.atBonus = fromLine(line);
        }
        else if (line.contains("Артефакты") && !line.contains("нет.")) {
            String[] tokens = line.split(":")[1].split(",");
            current.artifacts.addAll(Arrays.stream(tokens).map(String::trim).map(s -> s.replace(".","")).collect(Collectors.toList()));
        }
        else if (line.contains("Предметы") && !line.contains("нет.")) {
            current.items = fromLine(line);
        }
        else if (line.contains("Руны") && !line.contains("нет.")) {
            current.runes = fromLine(line);
        }
    }

    private int fromLine(String line) {
        return Integer.valueOf(line.split(":")[1].trim().replace(".", ""));
    }

    private Master addMaster(State state, String line) {
        Master master = new Master();
        state.masters.add(master);
        String[] tokens = line.split(" ");
        for (int i=0; i<tokens.length; i++) {
            if ("Владыка".equals(tokens[i])) {
                master.name = tokens[i+1].replace(".", "");
            }
        }
        return master;
    }

    private enum Mode {
        TEXT, MASTERS
    }
}
