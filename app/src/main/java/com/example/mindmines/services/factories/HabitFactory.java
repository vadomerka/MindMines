package com.example.mindmines.services.factories;

import com.example.mindmines.models.Habit;
import com.example.mindmines.models.dto.HabitDTO;
import com.example.mindmines.models.enums.HabitType;
import com.example.mindmines.services.repositories.HabitRepository;
import com.example.mindmines.views.habit.HabitInterval;

import java.time.OffsetDateTime;
import java.util.OptionalInt;

public class HabitFactory {
    private static final OptionalInt rm = HabitRepository.getAll() != null ? HabitRepository.getAll().stream().mapToInt(Habit::getHabitId).max() : OptionalInt.of(0);
    private static int localId = rm.isPresent() ? rm.getAsInt() : 0;

    public static HabitDTO createDTO(Integer userId, String title, String desc, Float frequency, Boolean timeAccurate,
                                     Integer priority, Integer difficulty, HabitType hType, HabitInterval interval) {
        return new HabitDTO(userId,
                title,
                desc,
                frequency,
                timeAccurate,
                priority,
                difficulty,
                hType,
                interval);
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

    public static HabitDTO createDTO(Integer userId) {
        return new HabitDTO(userId,
                "Название привычки",
                "Описание привычки",
                1.0f / 24,
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
}
