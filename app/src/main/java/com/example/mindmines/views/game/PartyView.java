package com.example.mindmines.views.game;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import com.example.mindmines.R;
import com.example.mindmines.views.BaseActivity;

public class PartyView extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button navButton = findViewById(R.id.bottom_navigation_bar4);
        navButton.setEnabled(false);


    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.character_party_view;
    }

    @Override
    protected Context getCurrentContext() {
        return this;
    }
}
