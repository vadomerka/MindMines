package com.example.mindmines.services;

import android.util.Log;
import android.widget.Button;

import com.example.mindmines.R;
import com.example.mindmines.models.Habit;
import com.example.mindmines.services.repositories.HabitRepository;

import java.time.OffsetDateTime;
import java.time.Period;

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
        Period interval = Period.of(0, 0, (int) Math.floor(h.getCheckingFrequency()));
        Log.d("Button update", String.format("lc: %s, lc+p: %s", lc, lc.plus(interval)));
        return lc.plus(interval).isBefore(n);
    }
}
