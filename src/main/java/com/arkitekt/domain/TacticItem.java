package com.arkitekt.domain;


public abstract class TacticItem {
    private String name;
    private TacticItemType type;

    public TacticItem(String name, TacticItemType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public TacticItemType getType() {
        return type;
    }
}
