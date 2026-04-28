package com.example.mindmines.views.game;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mindmines.R;
import com.example.mindmines.models.game.characters.Char;
import com.example.mindmines.models.game.equipment.CharEquipment;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.views.BaseFragment;
import com.google.android.material.button.MaterialButton;

public class CharView extends BaseFragment {
    private Char ch;

    public CharView() {
        super(R.layout.char_party_view);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (args == null) { return; }
        int chId = args.getInt("id", 0);
        ch = RepositoryService.getCharRepository().get(chId);

        LinearLayout statsView = requireView().findViewById(R.id.char_stats_view);
        LinearLayout equipView = requireView().findViewById(R.id.char_equipment_view);
        Button statsViewBtn = requireView().findViewById(R.id.char_stats_view_button);
        Button equipViewBtn = requireView().findViewById(R.id.char_equip_view_button);
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
        MaterialButton charBtn = requireView().findViewById(R.id.character_portrait_view);
        //                               2131165428
        Integer od = R.drawable.h1;
        charBtn.setIcon(requireContext().getDrawable(Integer.parseInt(ch.getImage())));

        TextView statTv0 = requireView().findViewById(R.id.name_value_view);
        TextView statTv1 = requireView().findViewById(R.id.level_value_view);
        TextView statTv2 = requireView().findViewById(R.id.exp_value_view);
        TextView statTv3 = requireView().findViewById(R.id.exp_until_next_value_view);
        TextView statTv4 = requireView().findViewById(R.id.attack_value_view);
        TextView statTv5 = requireView().findViewById(R.id.defence_value_view);
        TextView statTv6 = requireView().findViewById(R.id.speed_value_view);
        statTv0.setText(ch.getName());
        statTv1.setText(String.valueOf(ch.getStatus().getLevel()));
        statTv2.setText(String.valueOf(ch.getStatus().getExperience()));
        statTv3.setText(String.valueOf(ch.getStatus().getMaxExperience()));
        statTv4.setText(String.valueOf(ch.getStats().getAttack()));
        statTv5.setText(String.valueOf(ch.getStats().getDefence()));
        statTv6.setText(String.valueOf(ch.getStats().getSpeed()));

        MaterialButton leftHand = requireView().findViewById(R.id.open_equipment_btn1);
        MaterialButton rightHand = requireView().findViewById(R.id.open_equipment_btn2);
        MaterialButton bodyArmor = requireView().findViewById(R.id.open_equipment_btn3);
        MaterialButton legArmor = requireView().findViewById(R.id.open_equipment_btn4);
        CharEquipment chEq = ch.getEquipment();
        if (chEq.getLeftHand() != null) leftHand.setIcon(requireContext().getDrawable(Integer.parseInt(chEq.getLeftHand().getImage())));
        if (chEq.getRightHand() != null) rightHand.setIcon(requireContext().getDrawable(Integer.parseInt(chEq.getRightHand().getImage())));
        if (chEq.getBody() != null) bodyArmor.setIcon(requireContext().getDrawable(Integer.parseInt(chEq.getBody().getImage())));
        if (chEq.getLegs() != null) legArmor.setIcon(requireContext().getDrawable(Integer.parseInt(chEq.getLegs().getImage())));
    }
}
