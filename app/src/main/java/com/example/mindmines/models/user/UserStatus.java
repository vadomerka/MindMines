package com.example.mindmines.models.user;

import com.example.mindmines.models.XpStatus;
import com.example.mindmines.services.managers.XpManager;
import com.example.mindmines.models.interfaces.RepositoryItem;

public class UserStatus extends XpStatus implements RepositoryItem<String> {
    private String userId;

    public UserStatus(String userId) {
        this.userId = userId;
        this.level = 0;
        this.experience = 0L;
        this.maxExperience = XpManager.getBaseMaxExpChange();
        this.coins = 0L;
    }

    public UserStatus(
            String userId,
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

    public String getId() { return this.userId; }
    public void setId(String userID) { this.userId = userID; }

    public String getUserId() { return this.userId; }
    public void setUserId(String userID) { this.userId = userID; }

    public Long getCoins() { return this.coins; }

    public void setCoins(Long value) { this.coins = value; }
}
