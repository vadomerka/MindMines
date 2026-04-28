package com.example.mindmines.views.habit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindmines.MainActivity;
import com.example.mindmines.R;
import com.example.mindmines.infrastructure.HabitController;
import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.services.managers.UserStatusManager;
import com.example.mindmines.services.checkers.HabitCurrentCheckerService;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.views.BaseActivity;
import com.example.mindmines.services.observers.HabitObserver;
import com.example.mindmines.views.adapters.HabitCardAdapter;

import java.util.ArrayList;
import java.util.List;

public class HabitsView extends BaseActivity {
    private static final String TAG = "Debug HabitsView";
    private final HabitObserver hProxy = upd -> updateHabits();

    private AuthManager auth;
    private HabitCardAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI();
        if (MainActivity.isDebug()) { loadDebugTools(); }
    }

    private void initUI() {
        initListView();
        initButtons();
    }

    private void initListView() {
        RecyclerView listView = findViewById(R.id.habits_list_view);
        listView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        List<Habit> itemList = loadItemList();
        listAdapter = new HabitCardAdapter(itemList, this);
        listView.setAdapter(listAdapter);
    }

    private void initButtons() {
        Button add_btn = findViewById(R.id.add_habit_button);
        add_btn.setOnClickListener(v -> openHabitAddView());
        Button navButton = findViewById(R.id.bottom_navigation_bar3);
        navButton.setEnabled(false);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.habits_view;
    }

    @Override
    protected Context getCurrentContext() {
        return HabitsView.this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: 1");
        RepositoryService.getHabitRepository().subscribe(hProxy);
        hProxy.update(new ArrayList<>());
        UserStatusManager.subscribe(usProxy);
        usProxy.update(new ArrayList<>());
    }

    @Override
    protected void onStop() {
        RepositoryService.getHabitRepository().unsubscribe(hProxy);
        UserStatusManager.unsubscribe(usProxy);
        super.onStop();
    }

    private List<Habit> loadItemList() {
        return RepositoryService.getHabitRepository().getAll();
    }

    @SuppressLint("SetTextI18n")
    private void updateHabits() {
        Log.d(TAG, "updateHabits: updating view");
        runOnUiThread(() -> {
            List<HabitCardAdapter.CardViewHolder> cards = listAdapter.getCardViews();
            if (cards == null) return;
            for (HabitCardAdapter.CardViewHolder card: cards) {
                Habit h = RepositoryService.getHabitRepository().get(card.hId);

                card.streakTextView.setText(h.getStreakNumber().toString());
                card.penaltyTextView.setText(h.getPenaltyNumber().toString());

                HabitCurrentCheckerService.buttonViewUpdate(card.checkBtn);
            }
        });
    }

    public void openHabitAddView() {
        Intent myIntent = new Intent(HabitsView.this, HabitAddView.class);
        HabitsView.this.startActivity(myIntent);
        finish();
    }

    public void openHabitChangeView(int hId) {
        Intent myIntent = new Intent(HabitsView.this, HabitChangeView.class);
        myIntent.putExtra("id", hId);
        HabitsView.this.startActivity(myIntent);
        finish();
    }

    public void deleteHabit(int hId) {
        HabitController.getInstance(getCurrentContext()).delete(hId);
    }
}
