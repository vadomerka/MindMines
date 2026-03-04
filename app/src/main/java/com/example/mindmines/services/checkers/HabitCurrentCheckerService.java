package com.example.mindmines.services.checkers;

import android.graphics.Color;
import android.widget.Button;


import com.example.mindmines.R;
import com.example.mindmines.infrastructure.HabitManager;
import com.example.mindmines.models.Habit;
import com.example.mindmines.services.factories.HabitFactory;

import java.time.OffsetDateTime;


// Класс для изменений привычек на основе состояния в текущий момент.
public class HabitCurrentCheckerService {
    private static Boolean isHabitUnchecked(Habit h) {
        OffsetDateTime last = h.getLastCompletedAt();
        OffsetDateTime n = OffsetDateTime.now();
        OffsetDateTime ded = h.getNextDeadlineAt();
        if (ded == null) {
            // Debug, null deadlines should not be created.
            ded = HabitFactory.getNewNextDeadline(OffsetDateTime.now(), h.getInterval());
            h.setNextDeadlineAt(ded);
        }
        OffsetDateTime s = h.getPeriodStart();
        if (last == null) return true;
        // Если привычка отмечена в текущем периоде и дд не прошел.
        if (n.isAfter(ded)) return true;
        return !s.isBefore(last);
    }

    public static void buttonViewUpdate(Button btn) {
        Habit h = (Habit) btn.getTag();
        if (isHabitUnchecked(h))  {
            btn.setTextAppearance(R.style.UncheckedHabitButton);
            btn.setBackgroundColor(Color.RED);
        } else {
            btn.setTextAppearance(R.style.CheckedHabitButton);
            btn.setBackgroundColor(Color.GREEN);
        }
    }

    public static void buttonStatusCheck(Button btn) {
        Habit h = (Habit) btn.getTag();
        if (isHabitUnchecked(h)) {
            h.setLastCompletedAt(OffsetDateTime.now());
        }
        buttonViewUpdate(btn);

        HabitManager.update(h);
    }
}
