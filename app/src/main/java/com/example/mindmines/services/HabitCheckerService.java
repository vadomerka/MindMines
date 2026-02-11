package com.example.mindmines.services;

import android.graphics.Color;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;

import com.example.mindmines.R;
import com.example.mindmines.models.Habit;
import com.example.mindmines.services.factories.HabitFactory;
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
            btn.setBackgroundColor(Color.RED);
//            btn.setBackgroundResource(R.color.habit_unchecked_btn_color);
        } else {
            btn.setTextAppearance(R.style.CheckedHabitButton);
            btn.setBackgroundColor(Color.GREEN);
//            btn.setBackgroundResource(R.color.habit_checked_btn_color);
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
            if (ded == null) {
                // Debug, null deadlines should not be created.
                ded = HabitFactory.getNewNextDeadline(OffsetDateTime.now(), h.getInterval());
                h.setNextDeadlineAt(ded);
            }
            OffsetDateTime s = h.getPeriodStart();
            if (last == null) return true;
            // Если привычка отмечена в текущем периоде и дд не прошел.
            if (ded.isBefore(n)) return true;
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
