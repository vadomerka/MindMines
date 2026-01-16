package com.example.mindmines.services;

import android.util.Log;
import android.widget.Button;

import com.example.mindmines.R;
import com.example.mindmines.models.Habit;
import com.example.mindmines.services.repositories.HabitRepository;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.temporal.TemporalAmount;

public class HabitCheckerService {
    public static void buttonCheck(Button btn) {
        Habit h = (Habit) btn.getTag();
        if (isHabitUnchecked(h)) {
            h.setLastChecked(OffsetDateTime.now());
        } else {
            h.setLastChecked(OffsetDateTime.MIN);
        }
        buttonUpdate(btn);
        HabitRepository.update(h);
    }

    public static void buttonUpdate(Button btn) {
        Habit h = (Habit) btn.getTag();
        if (isHabitUnchecked(h))  {
            btn.setTextAppearance(R.style.UncheckedHabitButton);
        } else {
            btn.setTextAppearance(R.style.CheckedHabitButton);
        }
    }

    private static Boolean isHabitUnchecked(Habit h) {
        OffsetDateTime n = OffsetDateTime.now();
        OffsetDateTime lc = h.getLastChecked();
        if (lc == null) lc = OffsetDateTime.MIN;
        float d = h.getCheckingFrequency();
        return TimeIntervalService.plusFloat(lc, d).isBefore(n);
    }
}
