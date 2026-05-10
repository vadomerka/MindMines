package com.example.mindmines.views.game;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mindmines.R;
import com.example.mindmines.models.game.characters.Char;
import com.example.mindmines.models.game.equipment.CharEquipment;
import com.example.mindmines.models.game.equipment.types.Equipment;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.views.BaseFragment;
import com.google.android.material.button.MaterialButton;

public class CharView extends BaseFragment {
    private Char ch;

    public CharView() {
        super(R.layout.char_view);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackButtonVisible(true);

        Bundle args = getArguments();
        if (args == null) { return; }
        int chId = args.getInt("id", 0);
        ch = RepositoryService.getCharRepository().get(chId);

        LinearLayout statsView = requireActivity().findViewById(R.id.char_stats_view);
        LinearLayout equipView = requireActivity().findViewById(R.id.char_equipment_view);
        Button statsViewBtn = requireActivity().findViewById(R.id.char_stats_view_button);
        Button equipViewBtn = requireActivity().findViewById(R.id.char_equip_view_button);
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
        loadEquipData();
    }

    @Override
    public void onStop() {
        super.onStop();
        setBackButtonVisible(false);
    }

    @Override
    protected void returnBack() {
        NavHostFragment.findNavController(this).navigate(R.id.action_charFragment_to_partyFragment);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    protected void loadCharData() {
        MaterialButton charBtn = requireActivity().findViewById(R.id.character_portrait_view);
        charBtn.setIcon(requireContext().getDrawable(Integer.parseInt(ch.getImage())));

        TextView statTv0 = requireActivity().findViewById(R.id.name_value_view);
        TextView statTv1 = requireActivity().findViewById(R.id.level_value_view);
        TextView statTv2 = requireActivity().findViewById(R.id.exp_value_view);
        TextView statTv3 = requireActivity().findViewById(R.id.exp_until_next_value_view);
        TextView statTv4 = requireActivity().findViewById(R.id.attack_value_view);
        TextView statTv5 = requireActivity().findViewById(R.id.defence_value_view);
        TextView statTv6 = requireActivity().findViewById(R.id.speed_value_view);
        statTv0.setText(ch.getName());
        statTv1.setText(String.valueOf(ch.getStatus().getLevel()));
        statTv2.setText(String.valueOf(ch.getStatus().getExperience()));
        statTv3.setText(String.valueOf(ch.getStatus().getMaxExperience()));
        statTv4.setText(String.valueOf(ch.getStats().getAttack()));
        statTv5.setText(String.valueOf(ch.getStats().getDefence()));
        statTv6.setText(String.valueOf(ch.getStats().getSpeed()));
    }

    protected void loadEquipData() {
        MaterialButton leftHand = requireActivity().findViewById(R.id.open_equipment_btn1);
        MaterialButton rightHand = requireActivity().findViewById(R.id.open_equipment_btn2);
        MaterialButton bodyArmor = requireActivity().findViewById(R.id.open_equipment_btn3);
        MaterialButton legArmor = requireActivity().findViewById(R.id.open_equipment_btn4);
        CharEquipment chEq = ch.getEquipment();
        if (chEq.getLeftHand() != null) leftHand.setIcon(getIcon(chEq.getLeftHand()));
        if (chEq.getRightHand() != null) rightHand.setIcon(getIcon(chEq.getRightHand()));
        if (chEq.getBody() != null) bodyArmor.setIcon(getIcon(chEq.getBody()));
        if (chEq.getLegs() != null) legArmor.setIcon(getIcon(chEq.getLegs()));

        legArmor.setOnClickListener(v -> openShop(chEq.getLeftHand()));
    }

    public void openShop(Equipment eq) {
        new ShopView(requireContext(), getLayoutInflater()).startShop(eq);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private Drawable getIcon(Equipment eq) {
        return requireContext().getDrawable(Integer.parseInt(eq.getImage()));
    }
}
