package com.arkitekt.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TextFileLoader {

    public static List<String> load(String file) {
        try {
            return loadUnsafe(file);
        } catch (IOException ex) {
            System.out.println("error parsing file " + file);
            return new ArrayList<>();
        }
    }

    public static void unload(String file, List<String> list) {
        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(file));
            for (String line : list) {
                buf.write(line + "\n");
            }
            buf.close();
        } catch (IOException e) {
            System.out.println("error saving file " + file);
        }
    }

    private static List<String> loadUnsafe(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        List<String> list = new ArrayList<>();
        while (line != null) {
            list.add(line);
            line = reader.readLine();
        }
        reader.close();
        return list;
    }
}
