package com.example.mindmines.views.habit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

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

public class HabitAddView extends BaseActivity {
    protected WheelPicker itPicker;
    protected WheelPicker htPicker;
    protected boolean isBadType = false;
    protected EditText tEdit;
    protected EditText dEdit;
    protected Slider dSlider;
    protected Slider pSlider;
    protected Slider iSlider;
    protected NumberPicker cnPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.habit_add;
    }

    @Override
    protected Context getCurrentContext() {
        return HabitAddView.this;
    }

    protected void initUI() {
        initForm();
        initSliders();
        initTypeButtons();
        initWheelPickers();
        initSaveButton();
    }

    protected void initForm() {
        tEdit = findViewById(R.id.habit_title_value_edit);
        dEdit = findViewById(R.id.habit_desc_value_edit);
    }

    protected void initSliders() {
        dSlider = findViewById(R.id.habit_difficulty_value_slider);
        pSlider = findViewById(R.id.habit_priority_value_slider);
        iSlider = findViewById(R.id.habit_interval_number_value);
        cnPicker = findViewById(R.id.count_type_habit_count_value);
        cnPicker.setMinValue(1);
        cnPicker.setMaxValue(10);
    }

    protected void initTypeButtons() {
        Button goodThb = findViewById(R.id.good_type_habit_button);
        Button badThb = findViewById(R.id.bad_type_habit_button);
        LinearLayout htEl = findViewById(R.id.habit_type_edit_layout);
        goodThb.setEnabled(true);
        badThb.setEnabled(true);
        goodThb.setOnClickListener(v -> {
            isBadType = false;
            htEl.setVisibility(View.VISIBLE);
            goodThb.setEnabled(false);
            badThb.setEnabled(true);
        });
        badThb.setOnClickListener(v -> {
            isBadType = true;
            htEl.setVisibility(View.GONE);
            goodThb.setEnabled(true);
            badThb.setEnabled(false);
        });
    }

    protected void initWheelPickers() {
        itPicker = findViewById(R.id.habit_interval_type_value);
        htPicker = findViewById(R.id.habit_type_value);
        itPicker.setItems(new ArrayList<String>()
        {{ add ("Дни"); add ("Недели"); add ("Месяцы"); }});
        htPicker.setItems(new ArrayList<String>()
        {{ add ("Количественная"); add ("Интервальная"); add ("Подзадачи"); }});
        LinearLayout countThl = findViewById(R.id.count_type_habit_layout);
        LinearLayout taskThl = findViewById(R.id.task_type_habit_layout);
        htPicker.setOnWheelPickerListener(new OnWheelPickerListener() {
            @Override
            public void onItemSelected(int i, @NonNull String s) {
                countThl.setVisibility(View.GONE);
                taskThl.setVisibility(View.GONE);
                if (i == 0) { countThl.setVisibility(View.VISIBLE); }
                else if (i == 2) { taskThl.setVisibility(View.VISIBLE);}
            }

            @Override
            public void onRefreshed(@NonNull ArrayList<String> arrayList, int i, @NonNull String s) {
            }

            @Override
            public void onScrolling() {
            }
        });
    }

    protected void initSaveButton() {
        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> saveHabit());
    }

    protected void saveHabit() {
        Integer userId = Integer.valueOf(new AuthManager(getCurrentContext()).getUserId());
        String title = tEdit.getText().toString();
        String desc = dEdit.getText().toString();
        Integer goalCount = cnPicker.getValue();
        Boolean timeAccurate = false;
        Integer priority = (int) pSlider.getValue();
        Integer difficulty = (int) dSlider.getValue();
        HabitType hType;
        if (isBadType) hType = HabitType.BAD;
        else {
            HabitType[] types = new HabitType[] {HabitType.GOOD_GOAL_COUNT, HabitType.GOOD_INTERVAL, HabitType.GOOD_TASKS};
            hType = types[htPicker.getCurrentSelectedItemPosition()];
        }
        HabitInterval interval = HabitFactory.createHabitInterval(
                (int) iSlider.getValue(), itPicker.getCurrentSelectedItem());

        HabitDTO habitData = HabitFactory.createDTO(
                userId,
                title,
                desc,
                timeAccurate,
                priority,
                difficulty,
                hType,
                interval
        );
        HabitController.add(habitData);

        Intent myIntent = new Intent(HabitAddView.this, HabitsView.class);
        HabitAddView.this.startActivity(myIntent);
        finish();
    }
}