package com.example.mindmines.views.game.expedition;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindmines.R;
import com.example.mindmines.models.game.Expedition;
import com.example.mindmines.models.game.ExpeditionLocation;
import com.example.mindmines.services.factories.ExpeditionFactory;
import com.example.mindmines.services.managers.ExpeditionManager;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.views.adapters.LocationAdapter;
import com.example.mindmines.views.utils.ViewsUtils;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

public class ExpeditionView {
    private Context context;
    private LayoutInflater layoutInflater;

    private AlertDialog.Builder builder;
    private View dialogView;
    private Handler dialogTimerHandler;
    private Runnable dialogRunnable;

    private LocationAdapter adapter;
    private ImageView locationImage;
    private TextView locationTitle;
    private TextView timerText;
    private Button backButton;


    public ExpeditionView(Context context, LayoutInflater layoutInflater) {
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.builder = new AlertDialog.Builder(context);;
    }

    public void buildDialog(int id) {
        dialogView = layoutInflater.inflate(id, null);
        builder.setView(dialogView);
    }

    public void startExpedition() {
        buildDialog(R.layout.expedition_create_dialog);
        loadLocationsAdapter();

        AlertDialog dialog = builder.create();

        Button createButton = dialogView.findViewById(R.id.create_expedition_button);
        createButton.setOnClickListener(v -> {
            ExpeditionLocation selectedLocation = adapter.getSelectedLocation();
            if (selectedLocation == null) {
                Toast.makeText(context, "Выберите локацию", Toast.LENGTH_SHORT).show();
                return;
            }
            createNewExpedition(selectedLocation);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void loadLocationsAdapter() {
        List<ExpeditionLocation> locationList = RepositoryService.getExpeditionLocationRepository().getAll();
        RecyclerView recyclerView = dialogView.findViewById(R.id.location_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new LocationAdapter(locationList, (location, position) -> {});
        recyclerView.setAdapter(adapter);
    }

    private void createNewExpedition(ExpeditionLocation expeditionLocation) {
        ExpeditionManager.add(ExpeditionFactory.create(
                1,
                expeditionLocation.getName(),
                expeditionLocation.getImage(),
                Duration.ofMinutes(1) // TODO: заменить
        ));
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
        // Находим элементы диалога
        locationImage = dialogView.findViewById(R.id.expedition_location_image);
        locationTitle = dialogView.findViewById(R.id.expedition_location_title);
        timerText = dialogView.findViewById(R.id.expedition_timer);
        backButton = dialogView.findViewById(R.id.expedition_back_button);

        // Устанавливаем данные
        locationTitle.setText(ex.getTitle());
        // TODO: установить фото в зависимости от типа локации, пока заглушка
        locationImage.setImageResource(R.drawable.expedition_1);
    }

    private void startTimer(Expedition ex) {
        // Запускаем таймер внутри диалога (аналогично кнопке)
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


    public void finishExpedition() {
        Toast.makeText(context, "Завершение экспедиции (заглушка)", Toast.LENGTH_SHORT).show();
    }
}
