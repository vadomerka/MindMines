package com.example.mindmines.models;

import com.example.mindmines.models.enums.HabitType;

import java.time.OffsetDateTime;
import java.util.Date;

public class Habit {
    private final Integer habit_id;
    private final Integer user_id;
    private String title;
    private String description;
    private final OffsetDateTime creation_date;
    private Float checking_frequency;
    private Integer priority;
    private Integer difficulty;
    private HabitType type;
    private OffsetDateTime last_checked;
    private Integer penalty_number;
    private Integer streak_number;

    public Habit(
        Integer habit_id,
        Integer user_id,
        String title,
        String description,
        OffsetDateTime creation_date,
        Float checking_frequency,
        Integer priority,
        Integer difficulty,
        HabitType type,
        OffsetDateTime last_checked,
        Integer penalty_number,
        Integer streak_number
    ) {
        this.habit_id = habit_id;
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.creation_date = creation_date;
        this.checking_frequency = checking_frequency;
        this.priority = priority;
        this.difficulty = difficulty;
        this.type = type;
        this.last_checked = last_checked;
        this.penalty_number = penalty_number;
        this.streak_number = streak_number;
    }

    public Integer getHabitId() {
        return habit_id;
    }
    public Integer getUserId() {
        return user_id;
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
    public OffsetDateTime getCreationDate() {
        return creation_date;
    }
    public Float getCheckingFrequency() {
        return checking_frequency;
    }
    public void setCheckingFrequency(Float value) {
        checking_frequency = value;
    }
    public Integer getPriority() {
        return priority;
    }
    public void setPriority(Integer value) {
        priority = value;
    }
    public Integer getDifficulty() { return difficulty; }
    public void setDifficulty(Integer value) { difficulty = value; }
    public HabitType getType() {
        return type;
    }
    public void setType(HabitType value) { type = value; }
    public OffsetDateTime getLastChecked() {
        return last_checked;
    }
    public void setLastChecked(OffsetDateTime value) {
        last_checked = value;
    }
    public Integer getPenaltyNumber() {
        return penalty_number;
    }
    public Integer getStreakNumber() {
        return streak_number;
    }
}
