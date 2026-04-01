package com.example.mindmines.views.game;

import android.content.Context;

import com.example.mindmines.R;
import com.example.mindmines.views.BaseActivity;

public class PartyView extends BaseActivity {
    @Override
    protected int getContentLayoutId() {
        return R.layout.character_party_view;
    }

    @Override
    protected Context getCurrentContext() {
        return null;
    }
}
