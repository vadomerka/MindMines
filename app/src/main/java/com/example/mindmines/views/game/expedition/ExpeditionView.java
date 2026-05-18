package com.example.mindmines.views.game.expedition;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;

import com.example.mindmines.models.game.expeditions.Expedition;
import com.example.mindmines.services.managers.ExpeditionManager;
import com.example.mindmines.views.adapters.DialogAdapter;

public class ExpeditionView extends DialogAdapter {
    protected Handler dialogTimerHandler;
    protected Runnable dialogRunnable;


    public ExpeditionView(Context context, LayoutInflater layoutInflater) {
        super(context, layoutInflater);
    }

    public static void debugLogExpeditions(Context context) {
        Expedition ex = ExpeditionManager.getInstance(context).getLatestUnfinishedExpedition();
        if (ex == null) {
            Log.d("Debug Expeditions", "no unfinishedExpeditions");
            return;
        }
        Log.d("Debug Expeditions", ex.getTitle() + " " + ex.getFinish().toLocalTime() + " " + ex.isFinished());
    }
}
