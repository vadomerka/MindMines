package com.example.mindmines.services.factories;

import com.example.mindmines.db.entities.HabitEntity;
import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.models.habits.HabitTimeUnit;
import com.example.mindmines.models.habits.HabitDTO;
import com.example.mindmines.models.habits.HabitType;
import com.example.mindmines.services.repositories.HabitRepository;
import com.example.mindmines.models.habits.HabitInterval;

import java.time.OffsetDateTime;
import java.util.OptionalInt;

public class HabitFactory {
    private static final OptionalInt rm = HabitRepository.getAll() != null
            ? HabitRepository.getAll().stream().mapToInt(Habit::getHabitId).max()
            : OptionalInt.of(0);
    private static int localId = rm.isPresent() ? rm.getAsInt() : 0;

    public static HabitDTO createDTO(Integer userId, String title, String desc,
                                     Boolean timeAccurate, Integer priority, Integer difficulty,
                                     HabitType hType, HabitInterval interval) {
        return new HabitDTO(userId,
                title,
                desc,
                timeAccurate,
                priority,
                difficulty,
                hType,
                interval);
    }

    public static HabitDTO createDTO(Integer userId) {
        return new HabitDTO(userId,
                "Название привычки",
                "Описание привычки",
                true,
                1,
                1,
                HabitType.GOOD_INTERVAL,
                null);
    }

    public static Habit createFromDTO(HabitDTO dto) {
        return new Habit(
                ++localId,
                dto.getUserId(),
                dto.getType(),
                dto.getTitle(),
                dto.getDescription(),
                dto.getPriority(),
                dto.getDifficulty(),
                0,
                0,
                OffsetDateTime.now(),
                null,
                getNewNextDeadline(OffsetDateTime.now(), dto.getInterval()),
                dto.getInterval());
    }

    public static OffsetDateTime getNewNextDeadline(OffsetDateTime now, HabitInterval interval) {
        OffsetDateTime res = now;
        switch (interval.getTimeUnit()) {
            // Сложно отслеживать мягкое начало (+ 1), легче отслеживать неотмеченные привычки.
            case MINUTE:
                res = res
                        .plusMinutes(interval.getNumber())
                        .withSecond(0)
                        .minusSeconds(1);
                break;
            case HOUR:
                res = res
                        .plusHours(interval.getNumber())
                        .withMinute(0).withSecond(0)
                        .minusSeconds(1);
                break;
            case DAY:
                res = res
                        .plusDays(interval.getNumber())
                        .withHour(0).withMinute(0).withSecond(0)
                        .minusMinutes(1);
                break;
            case WEEK:
                res = res.plusDays(7L * (interval.getNumber()))
                        .minusDays(res.getDayOfWeek().getValue() - 1)
                        .withHour(0).withMinute(0).withSecond(0)
                        .minusMinutes(1);
                break;
            case MONTH:
                res = res
                        .plusMonths(interval.getNumber())
                        .withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0)
                        .minusMinutes(1);
                break;
        }
        return res;
    }

    public static Habit createFromEntity(HabitEntity e) {
        return new Habit(
                e.habitId,
                e.userId,
                habitTypeFromString(e.type),
                e.title,
                e.description,
                e.priority,
                e.difficulty,
                e.penaltyNumber,
                e.streakNumber,
                e.creationDate,
                e.lastCompletedAt,
                e.nextDeadlineAt,
                createHabitInterval(e.intervalNumber, e.intervalUnit));
    }

    public static HabitType habitTypeFromString(String type) {
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

    public static HabitInterval createHabitInterval(Integer num, String unit) {
        return new HabitInterval(num, intervalUnitFromString(unit));
    }

    public static HabitTimeUnit intervalUnitFromString(String type) {
        switch (type.toUpperCase()) {
            case "MINUTE":
            case "МИНУТА":
                return HabitTimeUnit.MINUTE;
            case "HOUR":
            case "ЧАС":
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

    public static HabitEntity createEntity(Habit h) {
        return new HabitEntity(
                h.getHabitId(),
                h.getUserId(),
                h.getType().toString(),
                h.getTitle(),
                h.getDescription(),
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
