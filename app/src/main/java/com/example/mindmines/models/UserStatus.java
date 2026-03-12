package com.example.mindmines.models;

public class UserStatus {
    private Integer userId;
    private Integer level;
    private Long experience;
    private Long maxExperience;

    public UserStatus() {
        this.userId = 0;
        this.level = 0;
        this.experience = 0L;
        this.maxExperience = 1L;
    }

    public UserStatus(
            Integer userId,
            Integer level,
            Long experience,
            Long maxExperience
    ) {
        this.userId = userId;
        this.level = level;
        this.experience = experience;
        this.maxExperience = maxExperience;
    }

    public Integer getUserId() { return this.userId; }

    public Integer getLevel() { return this.level; }

    public void setLevel(Integer value) { this.level = value; }

    public Long getExperience() { return this.experience; }

    public void setExperience(Long value) { this.experience = value; }

    public Long getMaxExperience() { return this.maxExperience; }

    public void setMaxExperience(Long value) { this.maxExperience = value; }
}
