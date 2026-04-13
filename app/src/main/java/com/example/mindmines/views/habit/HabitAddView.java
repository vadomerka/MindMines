package com.example.mindmines.views.habit;

import android.content.Context;
import android.os.Bundle;

import com.example.mindmines.R;
import com.example.mindmines.views.BaseActivity;
import com.github.vikramezhil.wheelpicker.view.WheelPicker;

import java.util.ArrayList;

public class HabitAddView extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WheelPicker picker = findViewById(R.id.habit_interval_type_value);
        picker.setItems(new ArrayList<String>() {
            {
                add ("Первый элемент");
                add ("Второй элемент");
                add ("Третий элемент");
            }
        });
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.habit_add;
    }

    @Override
    protected Context getCurrentContext() {
        return HabitAddView.this;
    }
}