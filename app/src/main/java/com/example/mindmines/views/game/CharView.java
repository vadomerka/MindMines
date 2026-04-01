package com.example.mindmines.views.game;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.mindmines.R;
import com.example.mindmines.views.BaseActivity;

public class CharView extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout statsView = findViewById(R.id.char_stats_view);
        LinearLayout equipView = findViewById(R.id.char_equipment_view);
        Button statsViewBtn = findViewById(R.id.char_stats_view_button);
        Button equipViewBtn = findViewById(R.id.char_equip_view_button);
        equipView.setVisibility(View.GONE);
        equipViewBtn.setOnClickListener(v -> {
            statsView.setVisibility(View.GONE);
            equipView.setVisibility(View.VISIBLE);
        });
        statsViewBtn.setOnClickListener(v -> {
            statsView.setVisibility(View.VISIBLE);
            equipView.setVisibility(View.GONE);
        });
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.character_view;
    }

    @Override
    protected Context getCurrentContext() {
        return this;
    }
}
