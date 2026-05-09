package com.example.mindmines.services.checkers;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.Button;


import com.example.mindmines.R;
import com.example.mindmines.infrastructure.HabitController;
import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.services.factories.HabitFactory;
import com.example.mindmines.services.managers.UserStatusManager;
import com.example.mindmines.services.repositories.RepositoryService;

import java.time.OffsetDateTime;


// Класс для изменений привычек на основе состояния в текущий момент.
public class HabitCurrentCheckerService extends BasicChecker {
    private static Boolean isHabitUnchecked(Habit h) {
        OffsetDateTime last = h.getLastCompletedAt();
        OffsetDateTime n = OffsetDateTime.now();
        OffsetDateTime ded = h.getNextDeadlineAt();

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

    public static void buttonStatusCheck(Button btn, Context context) {
        Habit h = (Habit) btn.getTag();
        // Если пользователь нажал кнопку, и привычка не была отмечена -> отмечаем.
        if (isHabitUnchecked(h)) {
            checkHabit(h, context);
        }
        buttonViewUpdate(btn);

        HabitController.getInstance(context).update(h);
    }

    private static void checkHabit(Habit h, Context context) {
        h.setLastCompletedAt(OffsetDateTime.now());

        h.setStreakNumber(h.getStreakNumber() + 1);
        h.setPenaltyNumber(0);
        HabitController.getInstance(context).update(h);

        UserStatusManager.getInstance(context).gain(h);
    }
}
