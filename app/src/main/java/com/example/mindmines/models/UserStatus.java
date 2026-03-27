package com.example.mindmines.models;

import com.example.mindmines.services.managers.ExpManager;

public class UserStatus {
    private Integer userId;
    private Integer level;
    private Long experience;
    private Long maxExperience;
    private Long coins;

    public UserStatus() {
        this.userId = 0;
        this.level = 0;
        this.experience = 0L;
        this.maxExperience = ExpManager.getBaseMaxExpChange();
        this.coins = 0L;
    }

    public UserStatus(
            Integer userId,
            Integer level,
            Long experience,
            Long maxExperience,
            Long coins
    ) {
        this.userId = userId;
        this.level = level;
        this.experience = experience;
        this.maxExperience = maxExperience;
        this.coins = coins;
    }

    public Integer getUserId() { return this.userId; }

    public Integer getLevel() { return this.level; }

    public void setLevel(Integer value) { this.level = value; }

    public Long getExperience() { return this.experience; }

    public void setExperience(Long value) { this.experience = value; }

    public Long getMaxExperience() { return this.maxExperience; }

    public void setMaxExperience(Long value) { this.maxExperience = value; }

    public Long getCoins() { return this.coins; }

    public void setCoins(Long value) { this.coins = value; }
}
