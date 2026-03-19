package com.example.mindmines.views.habit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindmines.R;
import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.services.checkers.HabitSyncCheckerService;
import com.example.mindmines.services.managers.UserStatusManager;
import com.example.mindmines.services.checkers.HabitCurrentCheckerService;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.services.repositories.HabitRepository;
import com.example.mindmines.views.BaseActivity;
import com.example.mindmines.views.observers.HabitObserver;
import com.example.mindmines.views.adapters.HabitCardAdapter;

import java.util.List;

public class HabitsView extends BaseActivity implements HabitObserver {
    private static final String TAG = "Debug data sync";

    private AuthManager auth;
    private HabitCardAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerView listView = findViewById(R.id.habits_list_view);
        listView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        List<Habit> itemList = loadItemList();
        listAdapter = new HabitCardAdapter(itemList, this);
        listView.setAdapter(listAdapter);

        Button add_btn = findViewById(R.id.add_habit_button);
        add_btn.setOnClickListener(v -> openHabitAddView());
        Button navButton = findViewById(R.id.bottom_navigation_bar3);
        navButton.setEnabled(false);

        loadDebugTools();
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
        Log.d(TAG, "onStart: 1");
        super.onStart();
        HabitRepository.subscribe(this);
        UserStatusManager.subscribe(this);
        updateHabits();
        updateUserStatus();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: 1");
        super.onStop();
        HabitRepository.unsubscribe(this);
        UserStatusManager.unsubscribe(this);
    }

    private List<Habit> loadItemList() {
        // TODO: implement sort and filters.
        // TODO: load only current user habits
        return HabitRepository.getAll();
    }

    @SuppressLint("SetTextI18n")
    public void updateHabits() {
        Log.d(TAG, "updateHabits: updating");
        runOnUiThread(() -> {
            List<HabitCardAdapter.CardViewHolder> cards = listAdapter.getCardViews();
            if (cards == null) return;
            for (HabitCardAdapter.CardViewHolder card: cards) {
                Habit h = HabitRepository.get(card.hId);

                card.streakTextView.setText(h.getStreakNumber().toString());
                card.penaltyTextView.setText(h.getPenaltyNumber().toString());

                HabitCurrentCheckerService.buttonViewUpdate(card.checkBtn);
            }
        });
    }

    public void openHabitChangeView(int hId) {
        Intent myIntent = new Intent(HabitsView.this, HabitChangeView.class);
        myIntent.putExtra("id", hId);
        HabitsView.this.startActivity(myIntent);
        finish();
    }

    public void openHabitAddView() {
        Intent myIntent = new Intent(HabitsView.this, HabitAddView.class);
        HabitsView.this.startActivity(myIntent);
        finish();
    }
}
