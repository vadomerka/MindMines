package com.example.mindmines.models;

import java.time.OffsetDateTime;
import java.util.Date;

public class Habit {
    private final Integer habit_id;
    private final Integer user_id;
    private final String title;
    private final String description;
    private final OffsetDateTime creation_date;
    private final Float checking_frequency;
    private final Integer priority;
    private final Integer difficulty;
    private final String type;
    private OffsetDateTime last_checked;
    private final Integer penalty_number;
    private final Integer streak_number;

    public Habit(
        Integer habit_id,
        Integer user_id,
        String title,
        String description,
        OffsetDateTime creation_date,
        Float checking_frequency,
        Integer priority,
        Integer difficulty,
        String type,
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
    public String getDescription() {
        return description;
    }
    public OffsetDateTime getCreationDate() {
        return creation_date;
    }
    public Float getCheckingFrequency() {
        return checking_frequency;
    }
    public Integer getPriority() {
        return priority;
    }
    public Integer getDifficulty() {
        return difficulty;
    }
    public String getType() {
        return type;
    }
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
