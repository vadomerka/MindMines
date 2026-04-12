package com.example.mindmines.views.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mindmines.R;
import com.example.mindmines.models.game.Char;
import com.example.mindmines.models.game.equipment.CharEquipment;
import com.example.mindmines.services.repositories.CharRepository;
import com.example.mindmines.views.BaseActivity;
import com.google.android.material.button.MaterialButton;

public class CharView extends BaseActivity {
    private Char ch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int chId = intent.getIntExtra("id", 0);
        ch = CharRepository.get(chId);

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

        loadCharData();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    protected void loadCharData() {
        MaterialButton charBtn = findViewById(R.id.character_portrait_view);
        //                               2131165428
        Integer od = R.drawable.h1;
        charBtn.setIcon(getDrawable(Integer.parseInt(ch.getImage())));

        TextView statTv0 = findViewById(R.id.name_value_view);
        TextView statTv1 = findViewById(R.id.level_value_view);
        TextView statTv2 = findViewById(R.id.exp_value_view);
        TextView statTv3 = findViewById(R.id.exp_until_next_value_view);
        TextView statTv4 = findViewById(R.id.attack_value_view);
        TextView statTv5 = findViewById(R.id.defence_value_view);
        TextView statTv6 = findViewById(R.id.speed_value_view);
        statTv0.setText(ch.getName());
        statTv1.setText(String.valueOf(ch.getStatus().getLevel()));
        statTv2.setText(String.valueOf(ch.getStatus().getExperience()));
        statTv3.setText(String.valueOf(ch.getStatus().getMaxExperience()));
        statTv4.setText(String.valueOf(ch.getStats().getAttack()));
        statTv5.setText(String.valueOf(ch.getStats().getDefence()));
        statTv6.setText(String.valueOf(ch.getStats().getSpeed()));

        MaterialButton leftHand = findViewById(R.id.open_equipment_btn1);
        MaterialButton rightHand = findViewById(R.id.open_equipment_btn2);
        MaterialButton bodyArmor = findViewById(R.id.open_equipment_btn3);
        MaterialButton legArmor = findViewById(R.id.open_equipment_btn4);
        CharEquipment chEq = ch.getEquipment();
        if (chEq.getLeftHand() != null) leftHand.setIcon(getDrawable(Integer.parseInt(chEq.getLeftHand().getImage())));
        if (chEq.getRightHand() != null) rightHand.setIcon(getDrawable(Integer.parseInt(chEq.getRightHand().getImage())));
        if (chEq.getBody() != null) bodyArmor.setIcon(getDrawable(Integer.parseInt(chEq.getBody().getImage())));
        if (chEq.getLegs() != null) legArmor.setIcon(getDrawable(Integer.parseInt(chEq.getLegs().getImage())));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.char_view;
    }

    @Override
    protected Context getCurrentContext() {
        return this;
    }
}
