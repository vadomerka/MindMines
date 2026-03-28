package com.example.mindmines.models.user;

import com.example.mindmines.models.ExpStatus;
import com.example.mindmines.services.managers.ExpManager;

public class UserStatus extends ExpStatus {
    private final Integer userId;

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

    public Long getCoins() { return this.coins; }

    public void setCoins(Long value) { this.coins = value; }
}
