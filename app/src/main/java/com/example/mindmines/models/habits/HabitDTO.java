package com.example.mindmines.models.habits;

import java.time.OffsetDateTime;

public class HabitDTO {
    private String user_id;
    private String title;
    private String description;
    private Integer goalCount;
    private Boolean timeAccurate;
    private Integer priority;
    private Integer difficulty;
    private HabitType type;
    private OffsetDateTime accTime;
    private HabitInterval interval;

    public HabitDTO(
            String user_id,
            String title,
            String description,
            Integer goalCount,
            Boolean timeAccurate,
            Integer priority,
            Integer difficulty,
            HabitType type,
            HabitInterval interval
    ) {
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.goalCount = goalCount;
        this.timeAccurate = timeAccurate;
        this.priority = priority;
        this.difficulty = difficulty;
        this.type = type;
        this.interval = interval;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String value) {
        user_id = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String value) {
        title = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        description = value;
    }

    public Integer getGoalCount() {
        return goalCount;
    }

    public void setGoalCount(Integer value) {
        goalCount = value;
    }

    public Boolean getTimeAccurate() {
        return timeAccurate;
    }

    public void setTimeAccurate(Boolean value) {
        timeAccurate = value;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer value) {
        priority = value;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer value) {
        difficulty = value;
    }

    public HabitType getType() {
        return type;
    }

    public void setType(HabitType value) {
        type = value;
    }

    public HabitInterval getInterval() {
        return interval;
    }

    public void setInterval(HabitInterval value) {
        interval = value;
    }
}

