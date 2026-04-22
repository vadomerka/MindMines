package com.example.mindmines.views.game;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mindmines.R;
import com.example.mindmines.models.game.Expedition;
import com.example.mindmines.models.game.characters.Char;
import com.example.mindmines.services.managers.ExpeditionManager;
import com.example.mindmines.services.repositories.ExpeditionRepository;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.views.BaseActivity;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class PartyView extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button navButton = findViewById(R.id.bottom_navigation_bar4);
        navButton.setEnabled(false);

        loadCharacterButtons();
        loadExpedition();
    }

    private void loadCharacterButtons() {
        Button charBtn1 = findViewById(R.id.open_character_btn1);
        Button charBtn2 = findViewById(R.id.open_character_btn2);
        Button charBtn3 = findViewById(R.id.open_character_btn3);
        Button charBtn4 = findViewById(R.id.open_character_btn4);
        List<Button> btnArr = new ArrayList<Button>() { {add(charBtn1); add(charBtn2); add(charBtn3); add(charBtn4); }};
        List<Char> chars = RepositoryService.getCharRepository().getAll();
        for (int i = 0; i < chars.size(); i++) {
            int finalI = i;
            btnArr.get(i).setEnabled(true);
            btnArr.get(i).setOnClickListener(v -> openCharView(chars.get(finalI).getCharId()));
        }
    }

    private void loadExpedition() {
        Expedition lExp = ExpeditionManager.getLatestUnfinishedExpedition();
        MaterialButton expBtn = findViewById(R.id.expedition_view_button);
        if (lExp == null) {
            expBtn.setText("Начать экспедицию");
            expBtn.setOnClickListener(v -> startExpedition());
            return;
        }

        boolean isEnded = ExpeditionManager.isEnded(lExp);
        if (isEnded) {
            expBtn.setText("Закончить экспедицию");
            expBtn.setBackgroundColor(Color.parseColor("#11FF00"));
            expBtn.setOnClickListener(v -> finishExpedition());
        } else {
            expBtn.setText("Осталось времени:");
            viewExpedition();
        }
    }

    private void startExpedition() {

    }

    private void finishExpedition() {

    }

    private void viewExpedition() {

    }

    public void openCharView(int chId) {
        Intent myIntent = new Intent(PartyView.this, CharView.class);
        myIntent.putExtra("id", chId);
        PartyView.this.startActivity(myIntent);
        finish();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.char_party_view;
    }

    @Override
    protected Context getCurrentContext() {
        return this;
    }
}
