package com.example.mindmines.views.game.expedition;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.mindmines.R;
import com.example.mindmines.models.game.expeditions.Expedition;
import com.example.mindmines.views.utils.ViewsUtils;

import java.time.Duration;
import java.time.OffsetDateTime;

public class ExpeditionTimerView extends ExpeditionView {
    private TextView timerText;
    private Button backButton;

    public ExpeditionTimerView(Context context, LayoutInflater layoutInflater) {
        super(context, layoutInflater);
    }

    public void viewExpedition(Expedition ex) {
        buildDialog(R.layout.expedition_view_dialog);

        loadForm(ex);
        startTimer(ex);

        AlertDialog dialog = builder.create();
        backButton.setOnClickListener(v -> {
            dialogTimerHandler.removeCallbacks(dialogRunnable);
            dialog.dismiss();
        });
        dialog.setOnDismissListener(dialogInterface -> dialogTimerHandler.removeCallbacks(dialogRunnable));
        dialog.show();
    }

    private void loadForm(Expedition ex) {
        ImageView locationImage = dialogView.findViewById(R.id.expedition_location_image);
        TextView locationTitle = dialogView.findViewById(R.id.expedition_location_title);
        timerText = dialogView.findViewById(R.id.expedition_timer);
        backButton = dialogView.findViewById(R.id.expedition_back_button);

        locationTitle.setText(ex.getTitle());

        locationImage.setImageResource(ex.getType() != null
                ? Integer.parseInt(ex.getType())
                : R.drawable.expedition_2);
    }

    private void startTimer(Expedition ex) {
        dialogTimerHandler = new Handler();
        dialogRunnable = new Runnable() {
            @Override
            public void run() {
                OffsetDateTime now = OffsetDateTime.now();
                OffsetDateTime finish = ex.getFinish();
                if (finish == null) return;
                Duration duration = Duration.between(now, finish);
                if (duration.isNegative() || duration.isZero()) {
                    timerText.setText("Завершено");
                    dialogTimerHandler.removeCallbacks(this);
                } else {
                    timerText.setText(ViewsUtils.parseDuration(duration));
                    dialogTimerHandler.postDelayed(this, 1000);
                }
            }
        };
        dialogTimerHandler.post(dialogRunnable);
    }
}
