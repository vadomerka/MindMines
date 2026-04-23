package com.example.mindmines.views.game;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;

import com.example.mindmines.R;
import com.example.mindmines.models.game.expeditions.Expedition;
import com.example.mindmines.models.game.characters.Char;
import com.example.mindmines.services.managers.ExpeditionManager;
import com.example.mindmines.services.repositories.ExpeditionRepository;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.views.BaseActivity;
import com.example.mindmines.views.game.expedition.ExpeditionView;
import com.example.mindmines.views.observers.ExpeditionObserver;
import com.example.mindmines.views.utils.ViewsUtils;
import com.google.android.material.button.MaterialButton;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class PartyView extends BaseActivity {
    private Handler timerHandler = new Handler(Looper.getMainLooper());
    private ExpeditionRepository rep;
    private Runnable timerRunnable;
    private Expedition activeExpedition;
    private MaterialButton expBtn;
    private ExpeditionView expeditionView;
    private final ExpeditionObserver expeditionObserver = upd -> loadExpedition();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button navButton = findViewById(R.id.bottom_navigation_bar4);
        navButton.setEnabled(false);

        rep = RepositoryService.getExpeditionRepository();
        rep.subscribe(expeditionObserver);
        expeditionView = new ExpeditionView(this, getLayoutInflater());
        loadCharacterButtons();
        loadExpedition();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.char_party_view;
    }

    @Override
    protected Context getCurrentContext() {
        return this;
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
        expBtn = findViewById(R.id.expedition_view_button);
        if (lExp == null) {
            expBtn.setText("Начать экспедицию");
            expBtn.setBackgroundColor(Color.parseColor("#FF6200EE"));
            expBtn.setOnClickListener(v -> expeditionView.startExpedition());
            stopTimer();
            return;
        }
        reloadExpedition(lExp);
    }

    private void reloadExpedition(Expedition lExp) {
        boolean isEnded = ExpeditionManager.isEnded(lExp);
        if (isEnded) {
            expBtn.setText("Собрать награду");
            expBtn.setBackgroundColor(Color.parseColor("#11FF00"));
            expBtn.setOnClickListener(v -> expeditionView.finishExpedition());
            stopTimer();
        } else {
            activeExpedition = lExp;
            startTimer(expBtn, lExp);
            expBtn.setOnClickListener(v -> expeditionView.viewExpedition(lExp));
        }
    }

    private void startTimer(MaterialButton button, Expedition expedition) {
        stopTimer();
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                updateTimerText(button, expedition);
                timerHandler.postDelayed(this, 1000);
            }
        };
        timerHandler.post(timerRunnable);
    }

    private void stopTimer() {
        if (timerRunnable != null) {
            timerHandler.removeCallbacks(timerRunnable);
            timerRunnable = null;
        }
    }

    private void updateTimerText(MaterialButton button, Expedition expedition) {
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime finish = expedition.getFinish();
        if (finish == null) return;

        Duration duration = Duration.between(now, finish);
        if (duration.isNegative() || duration.isZero()) {
            // Экспедиция завершилась, обновим состояние
            reloadExpedition(expedition);
            return;
        }

        button.setText(ViewsUtils.parseDuration(duration));
        button.setBackgroundColor(Color.parseColor("#FFBB33"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadExpedition();
    }

    @Override
    protected void onStart() {
        super.onStart();
        rep.subscribe(expeditionObserver);
    }

    @Override
    protected void onStop() {
        super.onStop();
        rep.unsubscribe(expeditionObserver);
    }


    public void openCharView(int chId) {
        Intent myIntent = new Intent(PartyView.this, CharView.class);
        myIntent.putExtra("id", chId);
        PartyView.this.startActivity(myIntent);
        finish();
    }
}
