package com.example.mindmines.views.habit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindmines.R;
import com.example.mindmines.models.UserStatus;
import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.services.UserStatusManager;
import com.example.mindmines.services.checkers.HabitCurrentCheckerService;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.services.repositories.HabitRepository;
import com.example.mindmines.views.observers.HabitObserver;
import com.example.mindmines.views.adapters.CardAdapter;
import com.example.mindmines.views.observers.UserStatusObserver;

import java.util.List;

public class HabitsView extends AppCompatActivity implements HabitObserver, UserStatusObserver {
    private static final String TAG = "Debug data sync";

    private AuthManager auth;
    private RecyclerView listView;
    private CardAdapter listAdapter;
    private TextView levelView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.habits_view);

        levelView = findViewById(R.id.userStatus_view);

        listView = findViewById(R.id.habits_list_view);
        listView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        List<Habit> itemList = loadItemList();
        listAdapter = new CardAdapter(itemList, this);
        listView.setAdapter(listAdapter);

        Button add_btn = findViewById(R.id.add_habit_button);
        add_btn.setOnClickListener(v -> openHabitAddView());
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
            List<CardAdapter.CardViewHolder> cards = listAdapter.getCardViews();
            if (cards == null) return;
            for (CardAdapter.CardViewHolder card: cards) {
                Habit h = HabitRepository.get(card.hId);

                card.streakTextView.setText(h.getStreakNumber().toString());
                card.penaltyTextView.setText(h.getPenaltyNumber().toString());

                HabitCurrentCheckerService.buttonViewUpdate(card.checkBtn);
            }
        });
    }

    @SuppressLint("DefaultLocale")
    public void updateUserStatus() {
        Log.d(TAG, "updateInfo: updating");
        runOnUiThread(() -> {
            UserStatus status = UserStatusManager.getStatus();
            levelView.setText(String.format("Уровень: %d; Опыт: %d/%d",
                    status.getLevel(), status.getExperience(), status.getMaxExperience()));
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
