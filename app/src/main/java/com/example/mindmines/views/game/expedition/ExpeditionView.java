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
import com.example.mindmines.views.utils.ViewsUtils;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

public class ExpeditionView {
    protected final Context context;
    protected final LayoutInflater layoutInflater;
    protected final AlertDialog.Builder builder;
    protected View dialogView;
    protected Handler dialogTimerHandler;
    protected Runnable dialogRunnable;


    public ExpeditionView(Context context, LayoutInflater layoutInflater) {
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.builder = new AlertDialog.Builder(context);;
    }

    public void buildDialog(int id) {
        dialogView = layoutInflater.inflate(id, null);
        builder.setView(dialogView);
    }

    protected void dialogBuild() {
        buildDialog(R.layout.expedition_view_dialog);
    }

    protected void dialogShow() {
        AlertDialog dialog = builder.create();
        dialog.setOnDismissListener(dialogInterface -> dialogTimerHandler.removeCallbacks(dialogRunnable));
        dialog.show();
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
