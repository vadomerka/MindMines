package com.example.mindmines.views.adapters;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.mindmines.R;
import com.example.mindmines.models.habits.HabitInterval;
import com.example.mindmines.models.habits.HabitTimeUnit;
import com.example.mindmines.services.factories.HabitFactory;
import com.example.mindmines.views.utils.ViewsUtils;
import com.github.vikramezhil.wheelpicker.props.OnWheelPickerListener;
import com.github.vikramezhil.wheelpicker.view.WheelPicker;
import com.google.android.material.slider.Slider;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class IntervalPickerAdapter {
    private Slider numberSlider;
    private WheelPicker unitPicker;
    private String[] currentSliderLabels;

    private final Map<String, String[]> labelDict;

    private final View rootView;

    public IntervalPickerAdapter(View rootView) {
        this.rootView = rootView;

        labelDict = new HashMap<>();
        labelDict.put("Минуты", new String[] {"1", "5", "15", "30", "60"});
        labelDict.put("Часы", new String[] {"1", "3", "6", "8", "24"});
        labelDict.put("Дни", new String[] {"1", "3", "7", "30"});

        loadDurationSlider();
        loadUnitPicker();
    }

    private void loadDurationSlider() {
        numberSlider = rootView.findViewById(R.id.interval_number_value);
        numberSlider.setValue(0);
        setNumberSlider(new String[] {"1", "5", "15", "30", "60"});
    }

    private void setNumberSlider(String[] arr) {
        currentSliderLabels = arr;
        numberSlider.setStepSize(1);
        numberSlider.setValueFrom(0);
        numberSlider.setValueTo(arr.length - 1);
        numberSlider.setValue(Math.min(Math.max(0, numberSlider.getValue()), arr.length - 1));
        numberSlider.setLabelFormatter(value -> arr[(int) value]);
    }

    private void loadUnitPicker() {
        unitPicker = rootView.findViewById(R.id.interval_type_value);
        unitPicker.setVisibility(View.VISIBLE);
        ArrayList<String> units = new ArrayList<>(Arrays.asList("Минуты", "Часы", "Дни"));
        unitPicker.setItems(units);
        unitPicker.postDelayed(() -> unitPicker.setSelectedItemPosition(0), 200);
        unitPicker.setOnWheelPickerListener(new OnWheelPickerListener() {
            @Override
            public void onItemSelected(int i, @NonNull String s) {
                String[] labels = labelDict.get(s);
                if (labels == null) {labels = new String[] {"1", "5", "15", "30", "60"};}
                setNumberSlider(labels);
            }

            @Override
            public void onRefreshed(@NonNull ArrayList<String> arrayList, int i, @NonNull String s) {}

            @Override
            public void onScrolling() {}
        });
    }

    public void loadDefault() {
//        loadFromHabitInterval(new HabitInterval(1, HabitTimeUnit.MINUTE));
    }

    public void loadFromHabitInterval(HabitInterval hi) {
        numberSlider.setValue(0);
        String[] labels = labelDict.get("Минуты");

        int pos;
        switch(hi.getTimeUnit()) {
            case MINUTE:
                pos = 0;
                labels = labelDict.get("Минуты");
                break;
            case HOUR:
                pos = 1;
                labels = labelDict.get("Часы");
                break;
            case DAY:
                pos = 2;
                labels = labelDict.get("Дни");
                break;
            default:
                pos = 3;
        }

        setNumberSlider(labels);
        unitPicker.postDelayed(() -> unitPicker.setSelectedItemPosition(pos), 200);

        for (int i = 0; i < currentSliderLabels.length; i++) {
            if (currentSliderLabels[i].equals(String.valueOf(hi.getNumber()))) {
                numberSlider.setValue(i);
            }
        }
    }

    public Duration getCurrentDuration() {
        return ViewsUtils.getDurationTime(
                Integer.parseInt(currentSliderLabels[(int) numberSlider.getValue()]),
                unitPicker.getCurrentSelectedItem());
    }

    public HabitInterval getHabitInterval() {
        return HabitFactory.createHabitInterval(
                Integer.parseInt(currentSliderLabels[(int) numberSlider.getValue()]),
                unitPicker.getCurrentSelectedItem());
    }
}
