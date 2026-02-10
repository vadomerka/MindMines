package com.example.mindmines.models.dto;

import com.example.mindmines.models.enums.HabitType;
import com.example.mindmines.views.habit.HabitInterval;

import java.time.OffsetDateTime;
import java.util.Date;

public class HabitDTO {
    private Integer user_id;
    private String title;
    private String description;
    private Float checking_frequency;
    private Boolean timeAccurate;
    private Integer priority;
    private Integer difficulty;
    private HabitType type;
    private OffsetDateTime accTime;
    private HabitInterval interval;

    public HabitDTO(
            Integer user_id,
            String title,
            String description,
            Float checking_frequency,
            Boolean timeAccurate,
            Integer priority,
            Integer difficulty,
            HabitType type,
            HabitInterval interval
    ) {
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.checking_frequency = checking_frequency;
        this.timeAccurate = timeAccurate;
        this.priority = priority;
        this.difficulty = difficulty;
        this.type = type;
        this.interval = interval;
    }

    public Integer getUserId() {
        return user_id;
    }

    public void setUserId(Integer value) {
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

    public Float getCheckingFrequency() {
        return checking_frequency;
    }

    public void setCheckingFrequency(Float value) {
        checking_frequency = value;
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

