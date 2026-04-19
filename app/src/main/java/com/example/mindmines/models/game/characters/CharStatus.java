package com.example.mindmines.models.game.characters;

import com.example.mindmines.models.ExpStatus;

import java.io.Serializable;

public class CharStatus extends ExpStatus implements Serializable {
    private Integer hp;

    public CharStatus() {
        this.hp = 0;
        this.level = 1;
        this.experience = 0L;
        this.maxExperience = 1L;
    }

    public CharStatus(Integer hp, Integer level, Long experience, Long maxExperience) {
        this.hp = hp;
        this.level = level;
        this.experience = experience;
        this.maxExperience = maxExperience;
    }

    public Integer getHp() { return hp; }
    public void setHp(Integer value) { this.hp = value; }
}
