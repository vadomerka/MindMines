package com.example.mindmines.views.game.expedition;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.github.vikramezhil.wheelpicker.view.WheelPicker;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExpeditionView {
    private Context context;
    private LayoutInflater layoutInflater;

    private AlertDialog.Builder builder;
    private View dialogView;
    private Handler dialogTimerHandler;
    private Runnable dialogRunnable;

    private LocationAdapter adapter;
    private ImageView locationImage;
    private TextView locationTitle;
    private TextView timerText;
    private Button backButton;

    private List<MaterialButton> presetButtons;
    private LinearLayout customDurationLayout;
    private Slider durationSlider;

    private Duration selectedDuration;


    public ExpeditionView(Context context, LayoutInflater layoutInflater) {
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.builder = new AlertDialog.Builder(context);;
    }

    public void buildDialog(int id) {
        dialogView = layoutInflater.inflate(id, null);
        builder.setView(dialogView);
    }

    public void startExpedition() {
        buildDialog(R.layout.expedition_create_dialog);
        loadLocationsAdapter();
        loadPresetButtons();
        loadCustomDurationLayout();

        AlertDialog dialog = builder.create();

        Button createButton = dialogView.findViewById(R.id.create_expedition_button);
        createButton.setOnClickListener(v -> {
            ExpeditionLocation selectedLocation = adapter.getSelectedLocation();
            if (selectedLocation == null) {
                Toast.makeText(context, "Выберите локацию", Toast.LENGTH_SHORT).show();
                return;
            }
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
        customDurationLayout.setVisibility(View.GONE);
        loadDurationSlider();
        loadUnitPicker();
    }

    private void loadPresetButtons() {
        LinearLayout presetButtonsLayout = dialogView.findViewById(R.id.duration_presets_layout);
        presetButtons = new ArrayList<>();
        Duration[] presets = {Duration.ofMinutes(5), Duration.ofMinutes(30), Duration.ofHours(1)};
        for (Duration duration: presets) {
            MaterialButton presetButton = new MaterialButton(context, null, R.style.TimeOutlineButton);
            presetButton.setId(View.generateViewId());
            presetButton.setText(ViewsUtils.parsePresetDuration(duration));
            presetButton.setOnClickListener(v -> selectPreset(duration, presetButton));

            presetButtons.add(presetButton);
            presetButtonsLayout.addView(presetButton);
        }
    }

    private void loadDurationSlider() {
        durationSlider = dialogView.findViewById(R.id.duration_value_slider);
        durationSlider.setValueFrom(1);
        durationSlider.setValueTo(60);
    }

    private void loadUnitPicker() {
        WheelPicker durationUnitPicker = dialogView.findViewById(R.id.duration_unit_picker);
        ArrayList<String> units = new ArrayList<>(Arrays.asList("Минуты", "Часы", "Дни", "Недели"));
        durationUnitPicker.setItems(units);
        durationUnitPicker.setSelectedItem("Минуты");
    }

    private void selectPreset(Duration duration, MaterialButton activeButton) {
        resetPresetButtonsStyle();
        selectedDuration = duration;
        activeButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context,
                R.color.purple_background_color)));
        activeButton.setTextColor(ContextCompat.getColor(context, android.R.color.white));
        customDurationLayout.setVisibility(View.GONE);
    }

    private void resetPresetButtonsStyle() {
        for (MaterialButton btn : presetButtons) {
            btn.setBackgroundTintList(null);
            btn.setTextColor(ContextCompat.getColor(context, android.R.color.black));
            btn.setStrokeColorResource(R.color.white);
        }
    }

    private void createNewExpedition(ExpeditionLocation expeditionLocation) {
        ExpeditionManager.add(ExpeditionFactory.create(
                1,
                expeditionLocation.getName(),
                expeditionLocation.getImage(),
                selectedDuration
        ));
    }

    public void viewExpedition(Expedition ex) {
        buildDialog(R.layout.expedition_view_dialog);

        loadForm(ex);
        startTimer(ex);

        AlertDialog dialog = builder.create();
        backButton.setOnClickListener(v -> {
            dialogTimerHandler.removeCallbacks(dialogRunnable);
            dialog.dismiss();
        });
        dialog.setOnDismissListener(dialogInterface -> dialogTimerHandler.removeCallbacks(dialogRunnable));
        dialog.show();
    }

    private void loadForm(Expedition ex) {
        // Находим элементы диалога
        locationImage = dialogView.findViewById(R.id.expedition_location_image);
        locationTitle = dialogView.findViewById(R.id.expedition_location_title);
        timerText = dialogView.findViewById(R.id.expedition_timer);
        backButton = dialogView.findViewById(R.id.expedition_back_button);

        // Устанавливаем данные
        locationTitle.setText(ex.getTitle());
        // TODO: установить фото в зависимости от типа локации, пока заглушка
        locationImage.setImageResource(R.drawable.expedition_2);
    }

    private void startTimer(Expedition ex) {
        // Запускаем таймер внутри диалога (аналогично кнопке)
        dialogTimerHandler = new Handler();
        dialogRunnable = new Runnable() {
            @Override
            public void run() {
                OffsetDateTime now = OffsetDateTime.now();
                OffsetDateTime finish = ex.getFinish();
                if (finish == null) return;
                Duration duration = Duration.between(now, finish);
                if (duration.isNegative() || duration.isZero()) {
                    timerText.setText("Завершено");
                    dialogTimerHandler.removeCallbacks(this);
                } else {
                    timerText.setText(ViewsUtils.parseDuration(duration));
                    dialogTimerHandler.postDelayed(this, 1000);
                }
            }
        };
        dialogTimerHandler.post(dialogRunnable);
    }


    public void finishExpedition() {
        Toast.makeText(context, "Завершение экспедиции (заглушка)", Toast.LENGTH_SHORT).show();
    }
}
