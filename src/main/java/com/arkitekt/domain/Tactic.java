package com.arkitekt.domain;


public class Tactic {
    private final Unit unit;
    private final Magic magic;
    private final Hero hero;
    private final Rune rune;

    public Tactic(Unit unit, Magic magic, Hero hero, Rune rune) {
        this.unit = unit;
        this.magic = magic;
        this.hero = hero;
        this.rune = rune;
    }

    public Unit getUnit() {
        return unit;
    }

    public Magic getMagic() {
        return magic;
    }

    public Hero getHero() {
        return hero;
    }

    public Rune getRune() {
        return rune;
    }

    @Override
    public String toString() {
        return "Tactic{" +
                "unit=" + unit.getName() +
                ", magic=" + magic.getName() +
                ", hero=" + hero.getName() +
                ", rune=" + (rune == null ? "" : rune.getName()) +
                '}';
    }
}
