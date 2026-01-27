package com.example.mindmines.views.habit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mindmines.R;
import com.example.mindmines.controllers.HabitController;
import com.example.mindmines.models.Habit;
import com.example.mindmines.models.enums.HabitType;
import com.example.mindmines.services.HabitAdderService;
import static com.example.mindmines.services.TimeIntervalService.*;

import com.example.mindmines.services.factories.HabitFactory;
import com.example.mindmines.services.repositories.HabitRepository;
import com.google.android.material.slider.Slider;

import static com.example.mindmines.services.utils.UIUtils.*;

public class HabitChangeView extends HabitAddView {
    @Override
    protected void loadDefaultValues() {
        Habit h = HabitRepository.get(hId);
        editTitle.setText(h.getTitle());
        editDesc.setText(h.getDescription());
        editPriority.setText(intToString(h.getPriority()));
        editDifficulty.setText(intToString(h.getDifficulty()));
        editType.setChecked(h.getType() != HabitType.GOOD);

        int pos = floatFreqToPosition(h.getCheckingFrequency());
        editFrequencyPeriodSpinner.setSelection(pos);

        float prevValue = h.getCheckingFrequency() == 0 ? 1f :
                h.getCheckingFrequency() / PERIOD_COEF[pos];
        updateSelected(pos, prevValue);
        editFrequencyDisplay.setText(intToString(floatToInt(prevValue)));
    }

    @Override
    protected void saveChanges() {
        String title = editTitle.getText().toString();
        String desc = editDesc.getText().toString();
        Integer priority = Integer.parseInt(editPriority.getText().toString());
        Integer difficulty = Integer.parseInt(editDifficulty.getText().toString());
        HabitType hType = HabitType.GOOD;
        if (editType.isChecked()) hType = HabitType.BAD;

        float frequencyValue = Float.parseFloat(editFrequencyDisplay.getText().toString());
        int id = (int) editFrequencyPeriodSpinner.getSelectedItemId();
        float freqCoef = PERIOD_COEF[id];

        HabitAdderService.change(hId, title, desc, frequencyValue * freqCoef, priority, difficulty, hType);
    }

    @Override
    protected void returnToHabitsView() {
        Intent myIntent = new Intent(HabitChangeView.this, HabitsView.class);
        HabitChangeView.this.startActivity(myIntent);
        finish();
    }
}