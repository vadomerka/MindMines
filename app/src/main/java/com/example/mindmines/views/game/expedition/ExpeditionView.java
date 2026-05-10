package com.example.mindmines.views.game.expedition;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.mindmines.R;
import com.example.mindmines.models.game.expeditions.Expedition;
import com.example.mindmines.services.managers.ExpeditionManager;
import com.example.mindmines.views.adapters.DialogAdapter;
import com.example.mindmines.views.utils.ViewsUtils;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

public class ExpeditionView extends DialogAdapter {
    protected Handler dialogTimerHandler;
    protected Runnable dialogRunnable;


    public ExpeditionView(Context context, LayoutInflater layoutInflater) {
        super(context, layoutInflater);
    }

    public static void debugLogExpeditions() {
        Expedition ex = ExpeditionManager.getLatestUnfinishedExpedition();
        if (ex == null) {
            Log.d("Debug Expeditions", "no unfinishedExpeditions");
            return;
        }
        Log.d("Debug Expeditions", ex.getTitle() + " " + ex.getFinish().toLocalTime() + " " + ex.isFinished());
    }
}
