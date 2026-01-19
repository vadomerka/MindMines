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
import com.example.mindmines.models.Habit;
import com.example.mindmines.models.enums.HabitType;
import com.example.mindmines.services.HabitAdderService;
import static com.example.mindmines.services.TimeIntervalService.*;
import com.example.mindmines.services.repositories.HabitRepository;
import com.google.android.material.slider.Slider;

import static com.example.mindmines.services.utils.UIUtils.*;

public class HabitChangeView extends AppCompatActivity {
    private static final String[] PERIODS = new String[]{"часов", "дней", "недель", "месяцев"};
    private static final float[] MAX_PERIOD_NUM = new float[]{
            24f,
            7f,
            4f,
            12f};
    private static final float[] PERIOD_COEF = new float[]{
            1f / 24f,
            1f,
            7f,
            30f};

    private int hId;

    private EditText editTitle;
    private EditText editDesc;
    private EditText editPriority;
    private EditText editDifficulty;
    private SwitchCompat editType;


    private TextView editFrequencyDisplay;
    private Spinner editFrequencyPeriodSpinner;
    private Slider editFrequencyNumberSlider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_change);

        Intent intent = getIntent();
        hId = intent.getIntExtra("id", 0);

        initInputs();

        Button rn = findViewById(R.id.save_habit_changes);
        rn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
                returnToHabitsView();
            }
        });
    }

    private void initInputs() {
        editTitle = findViewById(R.id.edit_title);
        editDesc = findViewById(R.id.edit_desc);
        editPriority = findViewById(R.id.edit_priority);
        editDifficulty = findViewById(R.id.edit_difficulty);
        editType = findViewById(R.id.edit_type);

        Habit h = HabitRepository.get(hId);
        editTitle.setText(h.getTitle());
        editDesc.setText(h.getDescription());

        editFrequencyDisplay = findViewById(R.id.edit_checking_frequency_number_display);
        editFrequencyPeriodSpinner = findViewById(R.id.edit_checking_frequency_period_type_spinner);
        editFrequencyNumberSlider = findViewById(R.id.edit_checking_frequency_number_slider);

        int id = floatFreqToPosition(h.getCheckingFrequency());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, PERIODS);
        editFrequencyPeriodSpinner.setAdapter(adapter);
        editFrequencyPeriodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (id >= PERIODS.length) return;
                updateSelected((int) id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                updateSelected(id);
            }
        });
        editFrequencyPeriodSpinner.setSelection(id);

        editFrequencyNumberSlider.addOnChangeListener((slider, value, fromUser) -> {
            editFrequencyDisplay.setText(intToString(floatToInt(value)));
        });

        float prevValue = id == 0 ? 1f : h.getCheckingFrequency() / PERIOD_COEF[id];
        editFrequencyNumberSlider.setValue(prevValue);
        editFrequencyDisplay.setText(intToString(floatToInt(prevValue)));

        editPriority.setText(intToString(h.getPriority()));
        editDifficulty.setText(intToString(h.getDifficulty()));
        editType.setChecked(h.getType() != HabitType.GOOD);
    }

    private void updateSelected(int id) {
        editFrequencyNumberSlider.setValueTo(MAX_PERIOD_NUM[id]);
        float prev = editFrequencyNumberSlider.getValue();
        editFrequencyNumberSlider.setValue(Math.max(Math.min(prev, MAX_PERIOD_NUM[id]), 1));
    }

    private int floatFreqToPosition(float freq) {
        int[] checks = new int[] {getMonths(freq), getWeeks(freq), getDays(freq), getHours(freq)};
        int pos = 0;
        for (int i = 0; i < checks.length; i++) {
            if (checks[i] > 0) {
                pos = checks.length - i - 1;
                break;
            }
        }
        return pos;
    }

    private void saveChanges() {
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

    private void returnToHabitsView() {
        Intent myIntent = new Intent(HabitChangeView.this, HabitsView.class);
        HabitChangeView.this.startActivity(myIntent);
        finish();
    }
}