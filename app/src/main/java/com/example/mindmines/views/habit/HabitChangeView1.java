package com.example.mindmines.views.habit;

import android.content.Intent;

import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.models.habits.HabitType;

import com.example.mindmines.services.repositories.HabitRepository;
import com.example.mindmines.services.repositories.RepositoryService;

import static com.example.mindmines.services.utils.UIUtils.*;

public class HabitChangeView1 extends HabitAddView1 {
    @Override
    protected void loadDefaultValues() {
        HabitRepository rep = RepositoryService.getHabitRepository();

        Habit h = rep.get(hId);
        editTitle.setText(h.getTitle());
        editDesc.setText(h.getDescription());
        editPriority.setText(intToString(h.getPriority()));
        editDifficulty.setText(intToString(h.getDifficulty()));
        editType.setChecked(h.getType() != HabitType.GOOD_INTERVAL);

//        int pos = floatFreqToPosition(h.getCheckingFrequency());
//        editFrequencyPeriodSpinner.setSelection(pos);

//        float prevValue = h.getCheckingFrequency() == 0 ? 1f :
//                h.getCheckingFrequency() / PERIOD_COEF[pos];
//        updateSelected(pos, prevValue);
//        editFrequencyDisplay.setText(intToString(floatToInt(prevValue)));
    }

    @Override
    protected void saveChanges() {
        String title = editTitle.getText().toString();
        String desc = editDesc.getText().toString();
        Integer priority = Integer.parseInt(editPriority.getText().toString());
        Integer difficulty = Integer.parseInt(editDifficulty.getText().toString());
        HabitType hType = HabitType.GOOD_INTERVAL;
        if (editType.isChecked()) hType = HabitType.BAD;

        float frequencyValue = Float.parseFloat(editFrequencyDisplay.getText().toString());
        int id = (int) editFrequencyPeriodSpinner.getSelectedItemId();
//        float freqCoef = PERIOD_COEF[id];

//        HabitAdderService.change(hId, title, desc, frequencyValue * freqCoef, priority, difficulty, hType);
    }

    @Override
    protected void returnToHabitsView() {
        Intent myIntent = new Intent(HabitChangeView1.this, HabitsView.class);
        HabitChangeView1.this.startActivity(myIntent);
        finish();
    }
}