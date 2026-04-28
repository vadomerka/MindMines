package com.example.mindmines.views.habit;

import android.content.Context;
import android.content.Intent;

import com.example.mindmines.R;
import com.example.mindmines.infrastructure.HabitController;
import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.models.habits.HabitDTO;
import com.example.mindmines.models.habits.HabitType;
import com.example.mindmines.services.repositories.HabitRepository;
import com.example.mindmines.services.repositories.RepositoryService;

public class HabitChangeView extends HabitAddView {
    @Override
    protected int getContentLayoutId() {
        return R.layout.habit_add;
    }

    @Override
    protected Context getCurrentContext() {
        return HabitChangeView.this;
    }

    @Override
    protected void loadValues() {
        HabitRepository rep = RepositoryService.getHabitRepository();
        Habit h = rep.get(habitId);

        hFormLoad(h);
        hIntervalLoad(h);
        hTypeLoad(h);
        hGoalLoad(h);
    }

    protected void hFormLoad(Habit h) {
        tEdit.setText(h.getTitle());
        dEdit.setText(h.getDescription());
        dSlider.setValue(h.getDifficulty());
        pSlider.setValue(h.getPriority());
    }

    protected void hIntervalLoad(Habit h) {
        int pos;
        switch(h.getInterval().getTimeUnit()) {
            case MINUTE:
                pos = 0;
                break;
            case DAY:
                pos = 1;
                break;
            case WEEK:
                pos = 2;
                break;
            default:
                pos = 3;
        }
        itPicker.setSelectedItemPosition(pos);
        iSlider.setValue(h.getInterval().getNumber());
    }

    protected void hTypeLoad(Habit h) {
        isBadType = h.getType() == HabitType.BAD;
        goodThb.setEnabled(isBadType);
        badThb.setEnabled(!isBadType);

        int pos;
        switch(h.getType()) {
            case GOOD_GOAL_COUNT:
                pos = 0;
                break;
            case GOOD_TASKS:
                pos = 2;
                break;
            default:
                pos = 1;
        }
        htPicker.setSelectedItemPosition(pos);
    }

    protected void hGoalLoad(Habit h) {
        gcPicker.setMinValue(1);
        gcPicker.setMaxValue(5);
        gcPicker.setValue(h.getGoalCount());

        // Здесь можно добавить загрузку мини задач.
    }

    @Override
    protected void saveHabit() {
        HabitDTO habitData = saveDto();

        // TODO: check data

        HabitController.getInstance(getCurrentContext()).change(habitId, habitData);
        exit();
    }

    @Override
    protected void exit() {
        Intent myIntent = new Intent(HabitChangeView.this, HabitsView.class);
        HabitChangeView.this.startActivity(myIntent);
        finish();
    }
}