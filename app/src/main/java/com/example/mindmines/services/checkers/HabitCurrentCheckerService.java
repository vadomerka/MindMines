package com.example.mindmines.services.checkers;

import android.graphics.Color;
import android.widget.Button;


import com.example.mindmines.R;
import com.example.mindmines.infrastructure.HabitController;
import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.services.factories.HabitFactory;
import com.example.mindmines.services.managers.UserStatusManager;

import java.time.OffsetDateTime;


// Класс для изменений привычек на основе состояния в текущий момент.
public class HabitCurrentCheckerService extends BasicChecker {
    private static Boolean isHabitUnchecked(Habit h) {
        String title = h.getTitle();
        OffsetDateTime last = h.getLastCompletedAt();
        OffsetDateTime n = OffsetDateTime.now();
        OffsetDateTime ded = h.getNextDeadlineAt();
        if (ded == null) {
            // Debug, null deadlines should not be created.
            ded = HabitFactory.getNewNextDeadline(OffsetDateTime.now(), h.getInterval());
            h.setNextDeadlineAt(ded);
        }
        OffsetDateTime s = h.getPeriodStart();
        if (ALWAYS_CHECKED) return false;
        if (last == null) return true;
        // Дедлайн прошел.
        if (n.isAfter(ded)) return true;
        // Отмечена ли привычка отмечена в текущем периоде.
        return !s.isBefore(last);
    }

    public static void buttonViewUpdate(Button btn) {
        Habit h = (Habit) btn.getTag();
        if (isHabitUnchecked(h))  {
            btn.setTextAppearance(R.style.UncheckedHabitButton);
            btn.setBackgroundColor(Color.parseColor("#D56363"));
        } else {
            btn.setTextAppearance(R.style.CheckedHabitButton);
            btn.setBackgroundColor(Color.parseColor("#6DFF8D"));
        }
    }

    public static void buttonStatusCheck(Button btn) {
        Habit h = (Habit) btn.getTag();
        // Если пользователь нажал кнопку, и привычка не была отмечена -> отмечаем.
        if (isHabitUnchecked(h)) {
            checkHabit(h);
        }
        buttonViewUpdate(btn);

        HabitController.update(h);
    }

    private static void checkHabit(Habit h) {
        h.setLastCompletedAt(OffsetDateTime.now());

        h.setStreakNumber(h.getStreakNumber() + 1);
        h.setPenaltyNumber(0);
        HabitController.update(h);

        UserStatusManager.gain(h);
        UserStatusManager.updateObservers();
    }
}
