package com.example.mindmines.views.game.expedition;

import android.content.Context;
import android.view.LayoutInflater;

import com.example.mindmines.models.game.expeditions.Expedition;
import com.example.mindmines.services.managers.ExpeditionManager;


public class ExpeditionFinishView extends ExpeditionView {
    public ExpeditionFinishView(Context context, LayoutInflater layoutInflater) {
        super(context, layoutInflater);
    }

    public void finishExpedition(Expedition lExp) {
        ExpeditionManager.finishExp(lExp);
    }
}
