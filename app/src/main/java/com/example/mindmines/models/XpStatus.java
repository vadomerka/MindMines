package com.example.mindmines.models;

public abstract class XpStatus {
    protected Integer level;
    protected Long experience;
    protected Long maxExperience;
    protected Long coins;

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer value) {
        this.level = value;
    }

    public Long getExperience() {
        return this.experience;
    }

    public void setExperience(Long value) {
        this.experience = value;
    }

    public Long getMaxExperience() {
        return this.maxExperience;
    }

    public void setMaxExperience(Long value) {
        this.maxExperience = value;
    }

    public Long getCoins() {
        return this.coins;
    }

    public void setCoins(Long value) {
        this.coins = value;
    }
}
