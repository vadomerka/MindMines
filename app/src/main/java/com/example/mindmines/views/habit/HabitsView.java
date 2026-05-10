package com.example.mindmines.views.habit;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindmines.R;
import com.example.mindmines.infrastructure.HabitController;
import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.services.checkers.HabitCurrentCheckerService;
import com.example.mindmines.services.observers.HabitObserver;
import com.example.mindmines.services.observers.UserStatusObserver;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.services.repositories.dao.HabitRepository;
import com.example.mindmines.views.BaseFragment;
import com.example.mindmines.views.adapters.HabitCardAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HabitsView extends BaseFragment {
    private static final String TAG = "Debug HabitsView";
    private final UserStatusObserver usProxy = upd -> updateUserStatus(null);
    private HabitCardAdapter listAdapter;
    private List<Habit> habitsList;
    private HabitRepository rep;
    private final HabitObserver hProxy = this::updateHabits;

    public HabitsView() {
        super(R.layout.habits_view);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rep = RepositoryService.getHabitRepository();
        initUI(view);
    }

    private void initUI(@NonNull View view) {
        RecyclerView listView = view.findViewById(R.id.habits_list_view);
        listView.setLayoutManager(new LinearLayoutManager(requireContext()));
        habitsList = loadItemList();
        listAdapter = new HabitCardAdapter(habitsList, this);
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
    private void updateHabits(List<Habit> upd) {
        if (!isAdded() || listAdapter == null) {
            return;
        }
        requireActivity().runOnUiThread(() -> {
            for (HabitCardAdapter.CardViewHolder card : listAdapter.getCardViews()) {
                Habit h = rep.get(card.hId);
                if (h == null) {
                    continue;
                }
                card.checkBtn.setTag(h);
                card.streakTextView.setText(h.getStreakNumber().toString());
                card.penaltyTextView.setText(h.getPenaltyNumber().toString());
                HabitCurrentCheckerService.buttonViewUpdate(card.checkBtn);
            }
        });
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
        List<Habit> deleted = habitsList.stream().filter(it -> it.getId() == hId).collect(Collectors.toList());
        if (deleted.isEmpty()) return;
        Habit posH = deleted.get(0);
        int pos = habitsList.indexOf(posH);
        habitsList.remove(pos);
        HabitController.getInstance(requireContext()).delete(hId);

        listAdapter.notifyItemRemoved(pos);
    }
}
