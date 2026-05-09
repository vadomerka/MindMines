package com.example.mindmines.models.game.characters;

import java.io.Serializable;

public class CharStats implements Serializable {
    private Integer attack;
    private Integer defence;
    private Integer speed;

    public CharStats() {
        this.attack = 10;
        this.defence = 10;
        this.speed = 10;
    }

    public CharStats(Integer attack, Integer defence, Integer speed) {
        this.attack = attack;
        this.defence = defence;
        this.speed = speed;
    }

    public Integer getAttack() { return attack; }
    public void setAttack(Integer value) { this.attack = value; }
    public Integer getDefence() { return defence; }
    public void setDefence(Integer value) { this.defence = value; }
    public Integer getSpeed() { return speed; }
    public void setSpeed(Integer value) { this.speed = value; }

    public void add(CharStats other) {
        this.attack += other.attack;
        this.defence += other.defence;
        this.speed += other.speed;
    }

    public void sub(CharStats other) {
        this.attack -= other.attack;
        this.defence -= other.defence;
        this.speed -= other.speed;
    }
}
