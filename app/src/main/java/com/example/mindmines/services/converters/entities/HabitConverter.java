package com.example.mindmines.services.converters.entities;

import com.example.mindmines.data.entities.HabitEntity;
import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.models.habits.HabitInterval;
import com.example.mindmines.models.habits.HabitTimeUnit;
import com.example.mindmines.models.habits.HabitType;

public class HabitConverter implements RepConverter<Integer, Habit, HabitEntity> {
    public Habit toItem(HabitEntity e) {
        return new Habit(
                e.habitId,
                e.userId,
                habitTypeFromString(e.type),
                e.title,
                e.description,
                e.currCount,
                e.goalCount,
                e.priority,
                e.difficulty,
                e.penaltyNumber,
                e.streakNumber,
                e.creationDate,
                e.lastCompletedAt,
                e.nextDeadlineAt,
                createHabitInterval(e.intervalNumber, e.intervalUnit));
    }

    public HabitType habitTypeFromString(String type) {
        switch (type.toUpperCase()) {
            case "GOOD_GOAL_COUNT":
                return HabitType.GOOD_GOAL_COUNT;
            case "GOOD_TASKS":
                return HabitType.GOOD_TASKS;
            case "GOOD_INTERVAL":
                return HabitType.GOOD_INTERVAL;
            default:
                return HabitType.BAD;
        }
    }

    public HabitInterval createHabitInterval(Integer num, String unit) {
        return new HabitInterval(num, intervalUnitFromString(unit));
    }

    public HabitTimeUnit intervalUnitFromString(String type) {
        switch (type.toUpperCase()) {
            case "MINUTE":
            case "МИНУТЫ":
                return HabitTimeUnit.MINUTE;
            case "HOUR":
            case "ЧАСЫ":
                return HabitTimeUnit.HOUR;
            case "DAY":
            case "ДНИ":
                return HabitTimeUnit.DAY;
            case "WEEK":
            case "НЕДЕЛИ":
                return HabitTimeUnit.WEEK;
            default:
                return HabitTimeUnit.MONTH;
        }
    }

    public HabitEntity toEntity(Habit h) {
        return new HabitEntity(
                h.getId(),
                h.getUserId(),
                h.getType().toString(),
                h.getTitle(),
                h.getDescription(),
                h.getCurrCount(),
                h.getGoalCount(),
                h.getPriority(),
                h.getDifficulty(),
                h.getPenaltyNumber(),
                h.getStreakNumber(),
                h.getCreationDate(),
                h.getLastCompletedAt(),
                h.getNextDeadlineAt(),
                h.getInterval().getNumber(),
                h.getInterval().getTimeUnit().toString()
        );
    }
}
