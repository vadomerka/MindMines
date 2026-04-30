package com.example.mindmines.views.habit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mindmines.R;
import com.example.mindmines.infrastructure.HabitController;
import com.example.mindmines.models.habits.HabitDTO;
import com.example.mindmines.models.habits.HabitInterval;
import com.example.mindmines.models.habits.HabitType;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.services.factories.HabitFactory;
import com.example.mindmines.views.BaseFragment;
import com.github.vikramezhil.wheelpicker.props.OnWheelPickerListener;
import com.github.vikramezhil.wheelpicker.view.WheelPicker;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;

public class HabitAddView extends BaseFragment {
    protected Integer habitId;
    protected String userId;

    protected WheelPicker itPicker;
    protected WheelPicker htPicker;
    protected boolean isBadType;
    protected EditText tEdit;
    protected EditText dEdit;
    protected Button goodThb;
    protected Button badThb;
    protected Slider dSlider;
    protected Slider pSlider;
    protected Slider iSlider;
    protected NumberPicker gcPicker;

    public HabitAddView() {
        super(R.layout.habit_add);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
        loadValues();
    }

    protected void initUI() {
        initVars();
        initForm();
        initSliders();
        initTypeButtons();
        initWheelPickers();
        initSaveButton();
    }

    protected void initVars() {
        Bundle args = getArguments();
        habitId = args == null ? 0 : args.getInt("id", 0);
        userId = new AuthManager(requireContext()).getUserId();
    }

    protected void loadValues() {
        gcPicker.setMinValue(1);
        gcPicker.setMaxValue(10);
        isBadType = false;
        goodThb.setEnabled(false);
        badThb.setEnabled(true);
        itPicker.setSelectedItemPosition(0);
        htPicker.setSelectedItemPosition(1);
    }

    protected void initForm() {
        View root = requireView();
        tEdit = root.findViewById(R.id.habit_title_value_edit);
        dEdit = root.findViewById(R.id.habit_desc_value_edit);
    }

    protected void initSliders() {
        View root = requireView();
        dSlider = root.findViewById(R.id.habit_difficulty_value_slider);
        pSlider = root.findViewById(R.id.habit_priority_value_slider);
        iSlider = root.findViewById(R.id.habit_interval_number_value);
        gcPicker = root.findViewById(R.id.count_type_habit_count_value);
    }

    protected void initTypeButtons() {
        View root = requireView();
        goodThb = root.findViewById(R.id.good_type_habit_button);
        badThb = root.findViewById(R.id.bad_type_habit_button);
        LinearLayout htEl = root.findViewById(R.id.habit_type_edit_layout);
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
        View root = requireView();
        itPicker = root.findViewById(R.id.habit_interval_type_value);
        itPicker.setItems(new ArrayList<String>()
        {{ add ("Минуты"); add ("Дни"); add ("Недели"); add ("Месяцы"); }});

        htPicker = root.findViewById(R.id.habit_type_value);
        htPicker.setItems(new ArrayList<String>()
        {{ add ("Количественная"); add ("Интервальная"); add ("Подзадачи"); }});
        LinearLayout countThl = root.findViewById(R.id.count_type_habit_layout);
        LinearLayout taskThl = root.findViewById(R.id.task_type_habit_layout);
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
        Button saveButton = requireView().findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> saveHabit());
    }

    protected HabitDTO saveDto() {
        String title = tEdit.getText().toString();
        String desc = dEdit.getText().toString();

        Boolean timeAccurate = false;
        Integer priority = (int) pSlider.getValue();
        Integer difficulty = (int) dSlider.getValue();
        HabitType hType;
        if (isBadType) hType = HabitType.BAD;
        else {
            HabitType[] types = new HabitType[] {HabitType.GOOD_GOAL_COUNT, HabitType.GOOD_INTERVAL, HabitType.GOOD_TASKS};
            hType = types[htPicker.getCurrentSelectedItemPosition()];
        }
        Integer goalCount = hType == HabitType.GOOD_GOAL_COUNT ? gcPicker.getValue() : 1;

        HabitInterval interval = HabitFactory.createHabitInterval(
                (int) iSlider.getValue(), itPicker.getCurrentSelectedItem());

        return HabitFactory.createDTO(userId, title, desc, goalCount, timeAccurate,
                                      priority, difficulty, hType, interval);
    }

    protected void saveHabit() {
        HabitDTO habitData = saveDto();

        // TODO: check data

        HabitController.getInstance(requireContext()).add(habitData);
        exit();
    }

    protected void exit() {
        NavHostFragment.findNavController(this).popBackStack();
    }
}