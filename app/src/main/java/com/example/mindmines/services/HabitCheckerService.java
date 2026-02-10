package com.example.mindmines.services;

import android.util.Log;
import android.widget.Button;

import com.example.mindmines.R;
import com.example.mindmines.models.Habit;
import com.example.mindmines.services.repositories.HabitRepository;
import com.example.mindmines.views.habit.HabitInterval;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.temporal.TemporalAmount;
import java.util.List;

public class HabitCheckerService {
    public static void buttonUpdate(Button btn) {
        Habit h = (Habit) btn.getTag();
        if (isHabitUnchecked(h))  {
            btn.setTextAppearance(R.style.UncheckedHabitButton);
        } else {
            btn.setTextAppearance(R.style.CheckedHabitButton);
        }
        HabitRepository.update(h);
    }

    public static void buttonCheck(Button btn) {
        Habit h = (Habit) btn.getTag();
        if (isHabitUnchecked(h)) {
            h.setLastCompletedAt(OffsetDateTime.now());
        }
        buttonUpdate(btn);
    }

    private static Boolean isHabitUnchecked(Habit h) {
        try {
            OffsetDateTime last = h.getLastCompletedAt();
            OffsetDateTime n = OffsetDateTime.now();
            OffsetDateTime ded = h.getNextDeadlineAt();
            OffsetDateTime s = h.getPeriodStart();
            if (last == null) return true;
            if (ded.isBefore(n)) {
                return true;
            }
            return !s.isBefore(last);
        } catch (NullPointerException e) {
            return true;
        }
    }

    public static void checkAllHabits() {
        List<Habit> hl = HabitRepository.getAll();
        for (Habit h: hl) {
            System.out.printf("Checking %s%n", h.getTitle());
            switch (h.getType()) {
                case GOOD_GOAL_COUNT:
                    break;
                case GOOD_TASKS:
                    break;
                case GOOD_INTERVAL:
                    if (h.getNextDeadlineAt().isBefore(OffsetDateTime.now())) {
                        h.setStreakNumber(0);
                        h.setPenaltyNumber(h.getPenaltyNumber() + 1);
                    } else {
                        h.setStreakNumber(h.getStreakNumber() + 1);
                        h.setPenaltyNumber(0);
                    }
                    h.setNextDeadlineAt(h.getNextNextDeadline());
                    break;
                default:
                    break;
            };
        }
    }
}
