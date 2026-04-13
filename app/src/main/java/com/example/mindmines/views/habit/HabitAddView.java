package com.example.mindmines.views.habit;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.example.mindmines.R;
import com.example.mindmines.views.BaseActivity;
import com.github.vikramezhil.wheelpicker.props.OnWheelPickerListener;
import com.github.vikramezhil.wheelpicker.view.WheelPicker;

import java.util.ArrayList;

public class HabitAddView extends BaseActivity {
    protected WheelPicker intervalTypePicker;
    protected WheelPicker habitTypePicker;
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
        initWheelPickers();
    }

    protected void initWheelPickers() {
        intervalTypePicker = findViewById(R.id.habit_interval_type_value);
        habitTypePicker = findViewById(R.id.habit_type_value);
        intervalTypePicker.setItems(new ArrayList<String>()
        {{ add ("Дни"); add ("Недели"); add ("Месяцы"); }});
        habitTypePicker.setItems(new ArrayList<String>()
        {{ add ("Количественная"); add ("Интервальная"); add ("Подзадачи"); }});
        LinearLayout countThl = findViewById(R.id.count_type_habit_layout);
        LinearLayout taskThl = findViewById(R.id.task_type_habit_layout);
        habitTypePicker.setOnWheelPickerListener(new OnWheelPickerListener() {
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
}