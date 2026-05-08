package com.example.mindmines.views.habit;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindmines.R;
import com.example.mindmines.infrastructure.HabitController;
import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.models.user.UserStatus;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.services.checkers.HabitCurrentCheckerService;
import com.example.mindmines.services.managers.UserStatusManager;
import com.example.mindmines.services.observers.HabitObserver;
import com.example.mindmines.services.observers.UserStatusObserver;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.views.BaseFragment;
import com.example.mindmines.views.adapters.HabitCardAdapter;

import java.util.ArrayList;
import java.util.List;

public class HabitsView extends BaseFragment {
    private static final String TAG = "Debug HabitsView";
    private final HabitObserver hProxy = upd -> updateHabits();
    private final UserStatusObserver usProxy = upd -> updateUserStatus();
    private HabitCardAdapter listAdapter;

    public HabitsView() {
        super(R.layout.habits_view);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
    }

    private void initUI(@NonNull View view) {
        RecyclerView listView = view.findViewById(R.id.habits_list_view);
        listView.setLayoutManager(new LinearLayoutManager(requireContext()));
        listAdapter = new HabitCardAdapter(loadItemList(), this);
        listView.setAdapter(listAdapter);

        Button addBtn = view.findViewById(R.id.add_habit_button);
        addBtn.setOnClickListener(v -> openHabitAddView());
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        RepositoryService.getHabitRepository().subscribe(hProxy);
        RepositoryService.getUserStatusRepository().subscribe(usProxy);
        hProxy.update(new ArrayList<>());
        usProxy.update(new ArrayList<>());
    }

    @Override
    public void onStop() {
        RepositoryService.getHabitRepository().unsubscribe(hProxy);
        RepositoryService.getUserStatusRepository().unsubscribe(usProxy);
        super.onStop();
    }

    private List<Habit> loadItemList() {
        return RepositoryService.getHabitRepository().getByUser();
    }

    @SuppressLint("SetTextI18n")
    private void updateHabits() {
        if (!isAdded() || listAdapter == null) {
            return;
        }
        requireActivity().runOnUiThread(() -> {
            List<HabitCardAdapter.CardViewHolder> cards = listAdapter.getCardViews();
            if (cards == null) {
                return;
            }
            for (HabitCardAdapter.CardViewHolder card : cards) {
                Habit h = RepositoryService.getHabitRepository().get(card.hId);
                if (h == null) {
                    continue;
                }
                card.streakTextView.setText(h.getStreakNumber().toString());
                card.penaltyTextView.setText(h.getPenaltyNumber().toString());
                HabitCurrentCheckerService.buttonViewUpdate(card.checkBtn);
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private void updateUserStatus() {
        if (!isAdded() || levelView == null) {
            return;
        }
        UserStatus status = new UserStatusManager(requireContext()).getStatus();
        levelView.setText(String.format("Уровень: %d; Опыт: %d/%d",
                status.getLevel(), status.getExperience(), status.getMaxExperience()));
    }

    public void openHabitAddView() {
        NavHostFragment.findNavController(this).navigate(R.id.action_habitsFragment_to_habitAddFragment);
    }

    public void openHabitChangeView(int hId) {
        Bundle args = new Bundle();
        args.putInt("id", hId);
        NavHostFragment.findNavController(this).navigate(R.id.action_habitsFragment_to_habitChangeFragment, args);
    }

    public void deleteHabit(int hId) {
        HabitController.getInstance(requireContext()).delete(hId);
    }
}
