package com.example.mindmines.models.habits;

import com.example.mindmines.models.enums.HabitType;

import java.time.OffsetDateTime;

public class Habit {
    private final Integer habitId;
    private final Integer userId;
    private HabitType type;

    private String title;
    private String description;
    private Integer priority;
    private Integer difficulty;
    private Integer penaltyNumber;
    private Integer streakNumber;

    private final OffsetDateTime creationDate;
    private OffsetDateTime lastCompletedAt;
    private OffsetDateTime nextDeadlineAt;
    private HabitInterval interval;

    public Habit(
            Integer habitId,
            Integer userId,
            HabitType type,

            String title,
            String description,
            Integer priority,
            Integer difficulty,
            Integer penaltyNumber,
            Integer streakNumber,

            OffsetDateTime creationDate,
            OffsetDateTime lastCompletedAt,
            OffsetDateTime nextDeadlineAt,
            HabitInterval interval
    ) {
        this.habitId = habitId;
        this.userId = userId;
        this.type = type;

        this.title = title;
        this.description = description;
        this.priority = priority;
        this.difficulty = difficulty;
        this.penaltyNumber = penaltyNumber;
        this.streakNumber = streakNumber;

        this.creationDate = creationDate;
        this.lastCompletedAt = lastCompletedAt;
        this.nextDeadlineAt = nextDeadlineAt;
        this.interval = interval;
    }

    public Integer getHabitId() {
        return habitId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getTitle() { return title; }

    public HabitType getType() { return type; }

    public void setType(HabitType value) { type = value; }


    public void setTitle(String value) { title = value; }

    public String getDescription() { return description; }

    public void setDescription(String value) { description = value; }

    public Integer getPriority() { return priority; }

    public void setPriority(Integer value) { priority = value; }

    public Integer getDifficulty() { return difficulty; }

    public void setDifficulty(Integer value) { difficulty = value; }

    public Integer getPenaltyNumber() { return penaltyNumber; }

    public void setPenaltyNumber(Integer value) { penaltyNumber = value; }

    public Integer getStreakNumber() { return streakNumber; }

    public void setStreakNumber(Integer value) { streakNumber = value; }



    public OffsetDateTime getCreationDate() { return creationDate; }

    public OffsetDateTime getLastCompletedAt() { return lastCompletedAt; }

    public void setLastCompletedAt(OffsetDateTime value) { lastCompletedAt = value; }

    public OffsetDateTime getNextDeadlineAt() { return nextDeadlineAt; }

    public void setNextDeadlineAt(OffsetDateTime value) { nextDeadlineAt = value; }

    public HabitInterval getInterval() { return interval; }

    public OffsetDateTime getPeriodStart() {
        OffsetDateTime start = nextDeadlineAt;
        if (interval == null) throw new NullPointerException();

        switch (interval.getTimeUnit()) {
            case MINUTE:
                start = nextDeadlineAt.minusMinutes(interval.getNumber());
                break;
            case HOUR:
                start = nextDeadlineAt.minusHours(interval.getNumber());
                break;
            case DAY:
                start = nextDeadlineAt.minusDays(interval.getNumber());
                break;
            case WEEK:
                start = nextDeadlineAt.minusWeeks(interval.getNumber());
                break;
            case MONTH:
                start = nextDeadlineAt.minusMonths(interval.getNumber());
                break;
        }
        return start;
    }

    public OffsetDateTime getNextNextDeadline() {
        OffsetDateTime end = nextDeadlineAt;
        switch (interval.getTimeUnit()) {
            case MINUTE:
                end = nextDeadlineAt.plusMinutes(interval.getNumber());
                break;
            case HOUR:
                end = nextDeadlineAt.plusHours(interval.getNumber());
                break;
            case DAY:
                end = nextDeadlineAt.plusDays(interval.getNumber());
                break;
            case WEEK:
                end = nextDeadlineAt.plusWeeks(interval.getNumber());
                break;
            case MONTH:
                end = nextDeadlineAt.plusMonths(interval.getNumber());
                break;
        }
        return end;
    }
}
