package com.example.mindmines.services.checkers;

import android.content.Context;
import android.util.Log;

import com.example.mindmines.infrastructure.HabitController;
import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.models.habits.HabitButtonStatus;
import com.example.mindmines.models.habits.HabitTimeUnit;
import com.example.mindmines.services.managers.UserStatusManager;
import com.example.mindmines.services.repositories.HabitRepository;
import com.example.mindmines.services.timers.HabitStatusCheckerTimer;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.temporal.TemporalUnit;
import java.util.List;


// Класс для изменений привычек на основе состояния в прошлом.
public class HabitSyncCheckerService extends BasicChecker {
    private static final String TAG = "Debug data sync";

    private static HabitButtonStatus wasHabitUnchecked(Habit h) {
        OffsetDateTime last = h.getLastCompletedAt();
        OffsetDateTime n = OffsetDateTime.now();
        OffsetDateTime ded = h.getNextDeadlineAt();
        OffsetDateTime s = h.getPeriodStart();
        // Период еще не кончился, проверять рано.
        if (n.isBefore(ded)) return HabitButtonStatus.TOO_EARLY_TO_CHECK;
        if (ALWAYS_CHECKED) return HabitButtonStatus.CHECKED;
        if (last == null) return HabitButtonStatus.NOT_CHECKED;
        // Если привычка отмечена до периода.
        if (last.isBefore(s)) return HabitButtonStatus.NOT_CHECKED;
        return HabitButtonStatus.CHECKED;
    }

    private static void habitStatusCheck(Habit h) {
        switch (h.getType()) {
            case GOOD_GOAL_COUNT:
                break;
            case GOOD_TASKS:
                break;
            case GOOD_INTERVAL:
                // Цикл симулирует проверки если телефон был выключен.
                if (h.getNextDeadlineAt().isBefore(OffsetDateTime.now())) {
                    // Подсчет отмеченных привычек.
                    HabitButtonStatus whu = wasHabitUnchecked(h);
                    if (whu == HabitButtonStatus.CHECKED) {
                        if (ALWAYS_CHECKED) {
                            h.setStreakNumber(h.getStreakNumber() + 1);
                            h.setPenaltyNumber(0);
                            UserStatusManager.gain(h);
                        }
                        h.setNextDeadlineAt(h.getNextNextDeadline(1));
                    }

                    OffsetDateTime d = h.getNextDeadlineAt();
                    OffsetDateTime n = OffsetDateTime.now();
                    Duration dur = Duration.between(d, n);
                    long missed;
                    if (h.getInterval().getTimeUnit() == HabitTimeUnit.MINUTE) {
                        missed = dur.toMinutes();
                    } else {
                        missed = dur.toDays();
                    }
                    missed /= h.getInterval().getNumber();
                    if (missed > 0) {
                        h.setStreakNumber(0);
                        h.setPenaltyNumber(h.getPenaltyNumber() + (int) missed);
                        UserStatusManager.gain(h);
                        // Вычисление следующего дедлайна.
                        h.setNextDeadlineAt(h.getNextNextDeadline((int) missed));
                    }
                }
                break;
            default:
                break;
        }

        HabitController.update(h);
    }

    // Метод обновляет стрики и пенальтии привычек по таймеру MidnightChecker
    public static void allHabitsCheck(Context context) {
        List<Habit> hl = HabitRepository.getAll();
        for (Habit h: hl) {
            habitStatusCheck(h);
        }
        UserStatusManager.updateObservers();
    }
}
