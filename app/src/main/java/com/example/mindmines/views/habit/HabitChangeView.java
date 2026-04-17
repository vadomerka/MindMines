package com.example.mindmines.views.habit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;

import com.example.mindmines.R;
import com.example.mindmines.infrastructure.HabitController;
import com.example.mindmines.models.habits.HabitDTO;
import com.example.mindmines.models.habits.HabitInterval;
import com.example.mindmines.models.habits.HabitType;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.services.factories.HabitFactory;
import com.example.mindmines.views.BaseActivity;
import com.github.vikramezhil.wheelpicker.props.OnWheelPickerListener;
import com.github.vikramezhil.wheelpicker.view.WheelPicker;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;

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
        // TODO: loadValues
        cnPicker.setMinValue(1);
        cnPicker.setMaxValue(10);
        isBadType = true;
        goodThb.setEnabled(true);
        badThb.setEnabled(false);
        itPicker.setSelectedItemPosition(2);
        htPicker.setSelectedItemPosition(2);
    }

    @Override
    protected void saveHabit() {
        HabitDTO habitData = saveDto();
        // TODO: HabitController.change
        HabitController.add(habitData);
        exit();
    }

    @Override
    protected void exit() {
        Intent myIntent = new Intent(HabitChangeView.this, HabitsView.class);
        HabitChangeView.this.startActivity(myIntent);
        finish();
    }
}