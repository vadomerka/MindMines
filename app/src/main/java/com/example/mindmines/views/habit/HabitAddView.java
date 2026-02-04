package com.example.mindmines.views.habit;

import static com.example.mindmines.services.TimeIntervalService.getDays;
import static com.example.mindmines.services.TimeIntervalService.getHours;
import static com.example.mindmines.services.TimeIntervalService.getMonths;
import static com.example.mindmines.services.TimeIntervalService.getWeeks;
import static com.example.mindmines.services.utils.UIUtils.floatToInt;
import static com.example.mindmines.services.utils.UIUtils.intToString;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.mindmines.R;
import com.example.mindmines.controllers.HabitController;
import com.example.mindmines.models.Habit;
import com.example.mindmines.models.NotifiBroadcastReciever;
import com.example.mindmines.models.dto.HabitDTO;
import com.example.mindmines.models.enums.HabitType;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.services.factories.HabitFactory;
import com.example.mindmines.services.notifications.HabitNotificationService;
import com.example.mindmines.services.repositories.HabitRepository;
import com.google.android.material.slider.Slider;

import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.Locale;
import android.Manifest;

public class HabitAddView extends AppCompatActivity {
    protected static final String[] PERIODS = new String[]{"часов", "дней", "недель", "месяцев"};
    protected static final float[] MAX_PERIOD_NUM = new float[]{
            24f,
            7f,
            4f,
            12f};
    protected static final float[] PERIOD_COEF = new float[]{
            1f / 24f,
            1f,
            7f,
            30f};

    protected int hId;
    protected int uId;

    protected EditText editTitle;
    protected EditText editDesc;
    protected EditText editPriority;
    protected EditText editDifficulty;
    protected SwitchCompat editType;

    protected CheckBox checkHabitPrecision;
    protected LinearLayout timePickerBox;
    protected TimePicker timePicker;

    protected TextView editFrequencyDisplay;
    protected Spinner editFrequencyPeriodSpinner;
    protected Slider editFrequencyNumberSlider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_add);

        Intent intent = getIntent();
        hId = intent.getIntExtra("id", 0);
        uId = Integer.parseInt(new AuthManager(getApplicationContext()).getUserId());

        initInputs();
        loadDefaultValues();

        Button rn = findViewById(R.id.save_habit_changes);
        rn.setOnClickListener(v -> {
            if (!isDataValid()) { showAlert(); }
            else {
                saveChanges();
                returnToHabitsView();
            }
        });
    }

    protected void initInputs() {
        editTitle = findViewById(R.id.edit_title);
        editDesc = findViewById(R.id.edit_desc);
        editPriority = findViewById(R.id.edit_priority);
        editDifficulty = findViewById(R.id.edit_difficulty);
        editType = findViewById(R.id.edit_type);
        editFrequencyDisplay = findViewById(R.id.edit_checking_frequency_number_display);
        editFrequencyPeriodSpinner = findViewById(R.id.edit_checking_frequency_period_type_spinner);
        editFrequencyNumberSlider = findViewById(R.id.edit_checking_frequency_number_slider);
        checkHabitPrecision = findViewById(R.id.check_habit_precision);
        timePickerBox = findViewById(R.id.timePickerBox);
        timePicker = findViewById(R.id.timePicker);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, PERIODS);
        editFrequencyPeriodSpinner.setAdapter(adapter);
        editFrequencyPeriodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (id >= PERIODS.length) return;
                updateSelected((int) id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                updateSelected(0);
            }
        });
        editFrequencyNumberSlider.addOnChangeListener((slider, value, fromUser) ->
                editFrequencyDisplay.setText(intToString(floatToInt(value))));

        checkHabitPrecision.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isChecked()) {
                timePickerBox.setVisibility(View.VISIBLE);
            } else {
                timePickerBox.setVisibility(View.GONE);
            }
        });
    }

    protected void loadDefaultValues() {
        HabitDTO h = HabitFactory.createDTO(uId);
        editTitle.setHint(h.getTitle());
        editDesc.setHint(h.getDescription());
        editPriority.setText(intToString(h.getPriority()));
        editDifficulty.setText(intToString(h.getDifficulty()));
        editType.setChecked(h.getType() != HabitType.GOOD);

        int pos = floatFreqToPosition(h.getCheckingFrequency());
        editFrequencyPeriodSpinner.setSelection(pos);

        float prevValue = pos == 0 ? 1f : h.getCheckingFrequency() / PERIOD_COEF[pos];
        updateSelected(pos, prevValue);
        editFrequencyDisplay.setText(intToString(floatToInt(prevValue)));
    }

    protected void updateSelected(int id) {
        updateSelected(id, editFrequencyNumberSlider.getValue());
    }

    protected void updateSelected(int id, float value) {
        editFrequencyNumberSlider.setValueTo(MAX_PERIOD_NUM[id]);
        editFrequencyNumberSlider.setValue(Math.max(Math.min(value, MAX_PERIOD_NUM[id]), 1));
    }

    protected int floatFreqToPosition(float freq) {
        int[] checks = new int[]{getMonths(freq), getWeeks(freq), getDays(freq), getHours(freq)};
        int pos = 0;
        for (int i = 0; i < checks.length; i++) {
            if (checks[i] > 0) {
                pos = checks.length - i - 1;
                break;
            }
        }
        return pos;
    }

    protected Boolean isDataValid() {
        return !editTitle.getText().toString().isEmpty();
    }

    protected void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HabitAddView.this);

        builder.setTitle("Ошибка сохранения!");
        builder.setMessage("Введите название привычки.");
        builder.setCancelable(true);
        builder.setNeutralButton("ок", (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    protected void saveChanges() {
        String title = editTitle.getText().toString();
        String desc = editDesc.getText().toString();
        Integer priority = Integer.parseInt(editPriority.getText().toString());
        Integer difficulty = Integer.parseInt(editDifficulty.getText().toString());
        HabitType hType = HabitType.GOOD;
        if (editType.isChecked()) hType = HabitType.BAD;

        float frequencyValue = Float.parseFloat(editFrequencyDisplay.getText().toString());
        int id = (int) editFrequencyPeriodSpinner.getSelectedItemId();
        float freqCoef = PERIOD_COEF[id];

        Habit h = HabitController.add(HabitFactory.createDTO(uId, title, desc,
                frequencyValue * freqCoef, true, priority, difficulty, hType));

        HabitNotificationService.scheduleDailyAlarm(this, h);

    }

    protected long getTime() {
        Calendar c = Calendar.getInstance();
        // TODO: заменить на данные пользователя.
        OffsetDateTime tNow = OffsetDateTime.now();
        c.set(tNow.getYear(), tNow.getMonthValue(), tNow.getDayOfMonth(), tNow.getHour(), tNow.getMinute() + 1);
        return c.getTimeInMillis();
    }

    protected void returnToHabitsView() {
        Intent myIntent = new Intent(HabitAddView.this, HabitsView.class);
        HabitAddView.this.startActivity(myIntent);
        finish();
    }
}