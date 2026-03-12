package com.example.mindmines.services.checkers;

import android.content.Context;
import android.util.Log;

import com.example.mindmines.infrastructure.HabitManager;
import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.models.enums.HabitButtonStatus;
import com.example.mindmines.services.repositories.HabitRepository;

import java.time.OffsetDateTime;
import java.util.List;


// Класс для изменений привычек на основе состояния в прошлом.
public class HabitSyncCheckerService {
    private static final String TAG = "Debug data sync";
    private static Integer maxWhile = 0;

    private static HabitButtonStatus wasHabitUnchecked(Habit h) {
        OffsetDateTime last = h.getLastCompletedAt();
        OffsetDateTime n = OffsetDateTime.now();
        OffsetDateTime ded = h.getNextDeadlineAt();
        OffsetDateTime s = h.getPeriodStart();
        // Период еще не кончился, проверять рано.
        if (n.isBefore(ded)) return HabitButtonStatus.TOO_EARLY_TO_CHECK;
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
                int countWhile = 0;
                while (h.getNextDeadlineAt().isBefore(OffsetDateTime.now())) {
                    countWhile++;
                    // Подсчет отмеченных привычек.
                    HabitButtonStatus whu = wasHabitUnchecked(h);
                    switch (whu) {
                        case NOT_CHECKED:
                            h.setStreakNumber(0);
                            h.setPenaltyNumber(h.getPenaltyNumber() + 1);
                            break;
                        case CHECKED:
                            h.setStreakNumber(h.getStreakNumber() + 1);
                            h.setPenaltyNumber(0);
                            break;
                    }



                    // Вычисление следующего дедлайна.
                    h.setNextDeadlineAt(h.getNextNextDeadline());
                }
                maxWhile = Math.max(maxWhile, countWhile);
                break;
            default:
                break;
        }

        HabitManager.update(h);
    }

    // Метод обновляет стрики и пенальтии привычек по таймеру MidnightChecker
    public static void allHabitsCheck(Context context) {
        maxWhile = 0;

        List<Habit> hl = HabitRepository.getAll();
        for (Habit h: hl) {
            habitStatusCheck(h);
        }

        Log.d(TAG, String.format("checkAllHabits: checked - maxWhile: %d", maxWhile));
    }
}
