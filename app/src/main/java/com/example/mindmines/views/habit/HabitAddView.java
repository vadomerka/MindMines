package com.example.mindmines.views.habit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mindmines.R;
import com.example.mindmines.infrastructure.HabitController;
import com.example.mindmines.models.habits.HabitDTO;
import com.example.mindmines.models.habits.HabitInterval;
import com.example.mindmines.models.habits.HabitType;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.services.factories.HabitFactory;
import com.example.mindmines.views.BaseFragment;
import com.example.mindmines.views.adapters.IntervalPickerAdapter;
import com.github.vikramezhil.wheelpicker.props.OnWheelPickerListener;
import com.github.vikramezhil.wheelpicker.view.WheelPicker;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;

public class HabitAddView extends BaseFragment {
    protected Integer habitId;
    protected String userId;
    protected WheelPicker htPicker;
    protected boolean isBadType;
    protected TextView tView;
    protected EditText tEdit;
    protected EditText dEdit;
    protected Button goodThb;
    protected Button badThb;
    protected Slider dSlider;
    protected Slider pSlider;
    protected NumberPicker gcPicker;
    protected IntervalPickerAdapter ipAdapter;

    protected View rootView;

    public HabitAddView() {
        super(R.layout.habit_add);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;

        initUI();
        loadValues();
    }

    protected void initUI() {
        initVars();
        initForm();
        initSliders();
        initTypeButtons();
        initPickers();
        initSaveButton();
    }

    protected void initVars() {
        Bundle args = getArguments();
        habitId = args == null ? 0 : args.getInt("id", 0);
        userId = new AuthManager(requireContext()).getUserId();
    }

    protected void initForm() {
        tView = rootView.findViewById(R.id.habit_add_title);
        tEdit = rootView.findViewById(R.id.habit_title_value_edit);
        dEdit = rootView.findViewById(R.id.habit_desc_value_edit);
    }

    protected void initSliders() {
        dSlider = rootView.findViewById(R.id.habit_difficulty_value_slider);
        pSlider = rootView.findViewById(R.id.habit_priority_value_slider);
        gcPicker = rootView.findViewById(R.id.count_type_habit_count_value);
    }

    protected void initTypeButtons() {
        goodThb = rootView.findViewById(R.id.good_type_habit_button);
        badThb = rootView.findViewById(R.id.bad_type_habit_button);
        LinearLayout htEl = rootView.findViewById(R.id.habit_type_edit_layout);
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

    protected void initPickers() {
        ipAdapter = new IntervalPickerAdapter(rootView);

        htPicker = rootView.findViewById(R.id.habit_type_value);
        htPicker.setItems(new ArrayList<String>()
        {{ add ("Количественная"); add ("Интервальная"); add ("Подзадачи"); }});
        LinearLayout countThl = rootView.findViewById(R.id.count_type_habit_layout);
        LinearLayout taskThl = rootView.findViewById(R.id.task_type_habit_layout);
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
        Button saveButton = rootView.findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> saveHabit());
    }

    protected void loadValues() {
        Log.d("Debug", "loadValues: ");
        gcPicker.setMinValue(1);
        gcPicker.setMaxValue(10);
        isBadType = false;
        goodThb.setEnabled(false);
        badThb.setEnabled(true);
        htPicker.postDelayed(() -> htPicker.setSelectedItemPosition(1), 200);
        ipAdapter.loadDefault();
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

        HabitInterval interval = ipAdapter.getHabitInterval();

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