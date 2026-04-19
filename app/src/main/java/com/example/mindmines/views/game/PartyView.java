package com.example.mindmines.views.game;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.mindmines.R;
import com.example.mindmines.models.game.characters.Char;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.views.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class PartyView extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button navButton = findViewById(R.id.bottom_navigation_bar4);
        navButton.setEnabled(false);

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
