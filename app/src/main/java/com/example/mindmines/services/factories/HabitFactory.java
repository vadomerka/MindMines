package com.example.mindmines.services.factories;

import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.models.habits.HabitDTO;
import com.example.mindmines.models.habits.HabitInterval;
import com.example.mindmines.models.habits.HabitTimeUnit;
import com.example.mindmines.models.habits.HabitType;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.services.repositories.dao.HabitRepository;

import java.time.OffsetDateTime;

public class HabitFactory {

    private static HabitFactory instance;
    private final HabitRepository rep;

    public HabitFactory() {
        this.rep = RepositoryService.getHabitRepository();
    }

    public static HabitFactory getInstance() {
        if (instance == null) {
            instance = new HabitFactory();
        }
        return instance;
    }

    public HabitDTO createDTO(String userId, String title, String desc, Integer goalCount,
                              Boolean timeAccurate, Integer priority, Integer difficulty,
                              HabitType hType, HabitInterval interval) {
        return new HabitDTO(userId,
                title,
                desc,
                goalCount,
                timeAccurate,
                priority,
                difficulty,
                hType,
                interval);
    }

    public HabitDTO createDTO(String userId) {
        return new HabitDTO(userId,
                "Название привычки",
                "Описание привычки",
                1,
                true,
                1,
                1,
                HabitType.GOOD_INTERVAL,
                null);
    }

    public Habit createFromDTO(HabitDTO dto) {
        return new Habit(
                rep.getId() + 1,
                dto.getUserId(),
                dto.getType(),
                dto.getTitle(),
                dto.getDescription(),
                dto.getGoalCount(),
                dto.getPriority(),
                dto.getDifficulty(),
                0,
                0,
                OffsetDateTime.now(),
                null,
                getNewNextDeadline(OffsetDateTime.now(), dto.getInterval()),
                dto.getInterval());
    }

    public Habit createFromDTO(Integer hId, HabitDTO dto) {
        Habit res = createFromDTO(dto);
        res.setHabitId(hId);
        return res;
    }

    public OffsetDateTime getNewNextDeadline(OffsetDateTime now, HabitInterval interval) {
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
}
