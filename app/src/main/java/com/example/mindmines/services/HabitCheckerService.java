package com.example.mindmines.services;

import android.widget.Button;

import com.example.mindmines.R;

public class HabitCheckerService {
    public static void buttonCheck(Button btn) {
        if (btn.getTag() == "checked") {
            btn.setTextAppearance(R.style.UncheckedHabitButton);
            btn.setTag("unchecked");

        } else {
            btn.setTextAppearance(R.style.CheckedHabitButton);
            btn.setTag("checked");
        }
    }
}
