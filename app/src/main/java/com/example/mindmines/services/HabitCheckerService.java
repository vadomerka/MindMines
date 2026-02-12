package com.example.mindmines.services;

import android.graphics.Color;
import android.widget.Button;


import com.example.mindmines.R;
import com.example.mindmines.models.Habit;
import com.example.mindmines.services.factories.HabitFactory;
import com.example.mindmines.services.repositories.HabitRepository;

import java.time.OffsetDateTime;
import java.util.List;


public class HabitCheckerService {
    public static void buttonUpdate(Button btn) {
        Habit h = (Habit) btn.getTag();
        if (isHabitUnchecked(h))  {
            btn.setTextAppearance(R.style.UncheckedHabitButton);
            btn.setBackgroundColor(Color.RED);
        } else {
            btn.setTextAppearance(R.style.CheckedHabitButton);
            btn.setBackgroundColor(Color.GREEN);
        }
    }

    public static void buttonCheck(Button btn) {
        Habit h = (Habit) btn.getTag();
        if (isHabitUnchecked(h)) {
            h.setLastCompletedAt(OffsetDateTime.now());
        }
        buttonUpdate(btn);
    }

    private static int wasHabitUnchecked(Habit h) {
        OffsetDateTime last = h.getLastCompletedAt();
        OffsetDateTime n = OffsetDateTime.now();
        OffsetDateTime ded = h.getNextDeadlineAt();
        OffsetDateTime s = h.getPeriodStart();
        if (last == null) return 0;
        // Если привычка отмечена в текущем периоде и период прошел.
        if (s.isAfter(last)) return 0;  // not checked
        if (n.isBefore(ded)) return 1;  // is checked, not was
        return 2;
    }

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

    public static void checkAllHabits() {
        List<Habit> hl = HabitRepository.getAll();
        for (Habit h: hl) {
            switch (h.getType()) {
                case GOOD_GOAL_COUNT:
                    break;
                case GOOD_TASKS:
                    break;
                case GOOD_INTERVAL:
                    int whu = wasHabitUnchecked(h);
                    if (whu == 0) {
                        h.setStreakNumber(0);
                        h.setPenaltyNumber(h.getPenaltyNumber() + 1);
                    } else if (whu == 2){
                        h.setStreakNumber(h.getStreakNumber() + 1);
                        h.setPenaltyNumber(0);
                    }
                    if (h.getNextDeadlineAt().isBefore(OffsetDateTime.now())) {
                        h.setNextDeadlineAt(h.getNextNextDeadline());
                    }
                    break;
                default:
                    break;
            }
            HabitRepository.update(h);
        }
    }
}
