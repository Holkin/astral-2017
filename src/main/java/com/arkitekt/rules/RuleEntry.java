package com.arkitekt.rules;


public class RuleEntry {
    private final int value;
    private final String key;

    public RuleEntry(int value, String key) {
        this.value = value;
        this.key = key;
    }

    public int getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }
}
