package com.example.mindmines.views.game;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mindmines.MainActivity;
import com.example.mindmines.R;
import com.example.mindmines.models.game.expeditions.Expedition;
import com.example.mindmines.models.game.characters.Char;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.services.managers.ExpeditionManager;
import com.example.mindmines.services.repositories.dao.ExpeditionRepository;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.views.BaseFragment;
import com.example.mindmines.views.game.expedition.ExpeditionFinishView;
import com.example.mindmines.views.game.expedition.ExpeditionStartView;
import com.example.mindmines.views.game.expedition.ExpeditionTimerView;
import com.example.mindmines.views.game.expedition.ExpeditionView;
import com.example.mindmines.services.observers.ExpeditionObserver;
import com.example.mindmines.views.utils.ViewsUtils;
import com.google.android.material.button.MaterialButton;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class PartyView extends BaseFragment {
    private final Handler timerHandler = new Handler(Looper.getMainLooper());
    private ExpeditionRepository rep;
    private Runnable timerRunnable;
    private MaterialButton expBtn;
    private ExpeditionStartView exStartView;
    private ExpeditionTimerView exView;
    private ExpeditionFinishView exFinishView;
    private final ExpeditionObserver expeditionObserver = upd -> updateExpedition();

    public PartyView() {
        super(R.layout.char_party_view);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rep = RepositoryService.getExpeditionRepository();

        loadExViews();
        loadCharacterButtons();
        loadExpedition();

        if (MainActivity.isDebug()) { loadDebugTools(); }
    }

    private void loadExViews() {
        exStartView = new ExpeditionStartView(requireContext(), getLayoutInflater());
        exView = new ExpeditionTimerView(requireContext(), getLayoutInflater());
        exFinishView = new ExpeditionFinishView(requireContext(), getLayoutInflater());
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void loadCharacterButtons() {
        MaterialButton charBtn1 = requireView().findViewById(R.id.open_character_btn1);
        MaterialButton charBtn2 = requireView().findViewById(R.id.open_character_btn2);
        MaterialButton charBtn3 = requireView().findViewById(R.id.open_character_btn3);
        MaterialButton charBtn4 = requireView().findViewById(R.id.open_character_btn4);
        List<MaterialButton> btnArr = new ArrayList<MaterialButton>() { {add(charBtn1); add(charBtn2); add(charBtn3); add(charBtn4); }};
        List<Char> chars = RepositoryService.getCharRepository().getByUser();
        for (int i = 0; i < chars.size(); i++) {
            int finalI = i;
            btnArr.get(i).setEnabled(true);
            btnArr.get(i).setOnClickListener(v -> openCharView(chars.get(finalI).getId()));
            btnArr.get(i).setIcon(requireContext().getDrawable(Integer.parseInt(chars.get(finalI).getImage())));
        }
    }

    public void openCharView(int chId) {
        Bundle args = new Bundle();
        args.putInt("id", chId);
        NavHostFragment.findNavController(this).navigate(R.id.action_partyFragment_to_charFragment, args);
    }

    private void updateExpedition() {
        if (MainActivity.isDebug()) { Log.d("debug Expedition", "updateExpedition: "); }
        loadExpedition();
    }

    private void loadExpedition() {
        ExpeditionView.debugLogExpeditions();

        Expedition lExp = ExpeditionManager.getLatestUnfinishedExpedition();
        expBtn = requireView().findViewById(R.id.expedition_view_button);
        if (lExp == null) {
            expBtn.setText("Начать экспедицию");
            expBtn.setBackgroundColor(Color.parseColor("#FF6200EE"));
            expBtn.setOnClickListener(v -> exStartView.startExpedition());
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
            if (MainActivity.isDebug()) { Log.d("Debug Expedition", lExp.getTitle() + " " + lExp.isFinished()); }
            expBtn.setOnClickListener(v -> exFinishView.finishExpedition(lExp));
            stopTimer();
        } else {
            startTimer(expBtn, lExp);
            expBtn.setOnClickListener(v -> exView.viewExpedition(lExp));
        }
    }

    private void startTimer(MaterialButton button, Expedition expedition) {
        stopTimer();
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                updateTimerText(button, expedition);
                if (timerRunnable == this) {
                    timerHandler.postDelayed(this, 1000);
                }
            }
        };
        timerHandler.post(timerRunnable);
    }

    private void stopTimer() {
        if (timerRunnable != null) {
            Log.d("Debug Expedition", "stopTimer: ");
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
            if (MainActivity.isDebug()) { Log.d("debug Expedition", "updateTimerText: "); }
            reloadExpedition(expedition);
            return;
        }

        button.setText(ViewsUtils.parseDuration(duration));
        button.setBackgroundColor(Color.parseColor("#FFBB33"));
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        rep.subscribe(expeditionObserver);
        RepositoryService.getUserStatusRepository().subscribe(usProxy);
        usProxy.update(new ArrayList<>());
    }

    @Override
    public void onStop() {
        super.onStop();
        rep.unsubscribe(expeditionObserver);
        RepositoryService.getUserStatusRepository().unsubscribe(usProxy);
    }

    @Override
    protected void loadDebugTools() {
        Button deleteBut = requireView().findViewById(R.id.delete_last_expedition_debug_button);
        deleteBut.setVisibility(View.VISIBLE);
        deleteBut.setOnClickListener(v ->
                ExpeditionManager.removeLast(ExpeditionManager.getLatestUnfinishedExpedition()));

        Button updateBut = requireView().findViewById(R.id.update_last_expedition_debug_button);
        updateBut.setVisibility(View.VISIBLE);
        updateBut.setOnClickListener(v -> loadExpedition());
    }
}
