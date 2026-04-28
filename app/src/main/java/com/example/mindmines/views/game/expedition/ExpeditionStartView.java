package com.example.mindmines.views.game.expedition;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindmines.R;
import com.example.mindmines.models.game.expeditions.Expedition;
import com.example.mindmines.models.game.expeditions.ExpeditionLocation;
import com.example.mindmines.services.factories.ExpeditionFactory;
import com.example.mindmines.services.managers.ExpeditionManager;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.views.adapters.LocationAdapter;
import com.example.mindmines.views.utils.ViewsUtils;
import com.github.vikramezhil.wheelpicker.props.OnWheelPickerListener;
import com.github.vikramezhil.wheelpicker.view.WheelPicker;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExpeditionStartView extends ExpeditionView {
    private LocationAdapter adapter;

    private List<MaterialButton> presetButtons;
    private LinearLayout.LayoutParams presetButtonParams;
    private LinearLayout customDurationLayout;

    private Duration selectedDuration;
    private Slider durationSlider;
    private WheelPicker durationUnitPicker;
    private String[] currentSliderLabels;


    public ExpeditionStartView(Context context, LayoutInflater layoutInflater) {
        super(context, layoutInflater);
    }

    public void startExpedition() {
        buildDialog(R.layout.expedition_create_dialog);
        loadLocationsAdapter();
        loadCustomDurationLayout();
        loadPresetButtons();

        AlertDialog dialog = builder.create();

        Button createButton = dialogView.findViewById(R.id.create_expedition_button);
        createButton.setOnClickListener(v -> {
            ExpeditionLocation selectedLocation = adapter.getSelectedLocation();
            if (selectedLocation == null) {
                Toast.makeText(context, "Выберите локацию", Toast.LENGTH_SHORT).show();
                return;
            }
            if (customDurationLayout.getVisibility() == View.VISIBLE) {
                selectedDuration = ViewsUtils.getDurationTime(
                        Integer.parseInt(currentSliderLabels[(int) durationSlider.getValue()]),
                        durationUnitPicker.getCurrentSelectedItem());
            }
            if (selectedDuration == null) {
                Toast.makeText(context, "Выберите продолжительность", Toast.LENGTH_SHORT).show();
                return;
            }

            Log.d("debug Expedition start", "startExpedition: "
                    + durationSlider.getValue() + " "
                    + durationUnitPicker.getCurrentSelectedItem() + " "
                    + selectedDuration.getUnits() + " "
            );

            createNewExpedition(selectedLocation);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void loadLocationsAdapter() {
        List<ExpeditionLocation> locationList = RepositoryService.getExpeditionLocationRepository().getAll();
        RecyclerView recyclerView = dialogView.findViewById(R.id.location_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new LocationAdapter(locationList, (location, position) -> {});
        recyclerView.setAdapter(adapter);
    }

    private void loadCustomDurationLayout() {
        customDurationLayout = dialogView.findViewById(R.id.custom_duration_layout);
        selectedDuration = null;
        loadDurationSlider();
        loadUnitPicker();
    }

    private void loadPresetButtons() {
        LinearLayout presetButtonsLayout = dialogView.findViewById(R.id.duration_presets_layout);
        presetButtons = new ArrayList<>();
        Duration[] presets = {Duration.ofMinutes(5), Duration.ofMinutes(30), Duration.ofHours(1)};
        ContextThemeWrapper wrapper = new ContextThemeWrapper(context, R.style.TimeOutlineButton);
        MaterialButton presetButton;
        for (Duration duration: presets) {
            presetButton = createPresetTimeButton(wrapper, duration);
            presetButtonsLayout.addView(presetButton);
        }
        presetButton = createChooseTimeButton(wrapper);
        presetButtonsLayout.addView(presetButton);
        resetPresetButtonsStyle();

        selectPreset(selectedDuration, presetButton, View.VISIBLE);
    }

    private MaterialButton createPresetTimeButton(ContextThemeWrapper wrapper, Duration duration) {
        MaterialButton presetButton = new MaterialButton(wrapper);
        presetButton.setId(View.generateViewId());
        presetButton.setText(ViewsUtils.parsePresetDuration(duration));

        presetButton.setOnClickListener(v -> selectPreset(duration, presetButton, View.GONE));

        presetButtons.add(presetButton);
        return presetButton;
    }

    private MaterialButton createChooseTimeButton(ContextThemeWrapper wrapper) {
        MaterialButton presetButton = new MaterialButton(wrapper);
        presetButton.setId(View.generateViewId());
        presetButton.setText("Ввод");

        presetButton.setOnClickListener(v -> {
            selectedDuration = ViewsUtils.getDurationTime((int) durationSlider.getValue(),
                    durationUnitPicker.getCurrentSelectedItem());
            selectPreset(selectedDuration, presetButton, View.VISIBLE);
        });

        presetButtons.add(presetButton);
        return presetButton;
    }

    private void loadDurationSlider() {
        durationSlider = dialogView.findViewById(R.id.duration_value_slider);
        setDurationSlider(new String[] {"1", "5", "15", "30", "60"});
    }

    private void setDurationSlider(String[] arr) {
        currentSliderLabels = arr;
        durationSlider.setStepSize(1);
        durationSlider.setValueFrom(0);
        durationSlider.setValueTo(arr.length - 1);
        durationSlider.setValue(Math.min(Math.max(0, durationSlider.getValue()), arr.length - 1));
        durationSlider.setLabelFormatter(value -> arr[(int) value]);
    }

    private void loadUnitPicker() {
        durationUnitPicker = dialogView.findViewById(R.id.duration_unit_picker);
        durationUnitPicker.setVisibility(View.VISIBLE);
        ArrayList<String> units = new ArrayList<>(Arrays.asList("Минуты", "Часы", "Дни"));
        durationUnitPicker.setItems(units);
        durationUnitPicker.setSelectedItemPosition(0);
        durationUnitPicker.setOnWheelPickerListener(new OnWheelPickerListener() {
            @Override
            public void onItemSelected(int i, @NonNull String s) {
                switch (s) {
                    case "Минуты":
                        setDurationSlider(new String[] {"1", "5", "15", "30", "60"});
                        break;
                    case "Часы":
                        setDurationSlider(new String[] {"1", "3", "6", "8", "24"});
                        break;
                    case "Дни":
                        setDurationSlider(new String[] {"1", "3", "7", "30"});
                        break;
                }
            }

            @Override
            public void onRefreshed(@NonNull ArrayList<String> arrayList, int i, @NonNull String s) {}

            @Override
            public void onScrolling() {}
        });
    }

    private void selectPreset(Duration duration, MaterialButton activeButton, int visibility) {
        resetPresetButtonsStyle();
        selectedDuration = duration;
        activeButton.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_purple));
        activeButton.setTextColor(ContextCompat.getColor(context, android.R.color.white));

        customDurationLayout.setVisibility(visibility);
    }

    private void resetPresetButtonsStyle() {
        for (MaterialButton btn : presetButtons) {
            btn.setTextColor(ContextCompat.getColor(context, android.R.color.black));
            btn.setBackgroundColor(Color.WHITE);
        }
    }

    private void createNewExpedition(ExpeditionLocation expeditionLocation) {
        Log.d("debug Expedition", "createNewExpedition: ");
        Expedition result = ExpeditionFactory.create(
                1,
                expeditionLocation.getName(),
                expeditionLocation.getImage(),
                selectedDuration
        );
        ExpeditionManager.add(result);
    }
}
