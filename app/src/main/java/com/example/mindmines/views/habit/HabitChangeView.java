package com.example.mindmines.views.habit;

import androidx.navigation.fragment.NavHostFragment;

import com.example.mindmines.R;
import com.example.mindmines.infrastructure.HabitController;
import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.models.habits.HabitDTO;
import com.example.mindmines.models.habits.HabitType;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.services.repositories.dao.HabitRepository;

public class HabitChangeView extends HabitAddView {
    @Override
    protected void returnBack() {
        NavHostFragment.findNavController(this).navigate(R.id.action_habitChangeFragment_to_habitsFragment);
    }

    @Override
    protected void loadValues() {
        HabitRepository rep = RepositoryService.getHabitRepository();
        Habit h = rep.get(habitId);

        hFormLoad(h);
        hIntervalLoad(h);
        hTypeLoad(h);
        hGoalLoad(h);
    }

    protected void hFormLoad(Habit h) {
        tView.setText("Изменение привычки");
        tEdit.setText(h.getTitle());
        dEdit.setText(h.getDescription());
        dSlider.setValue(h.getDifficulty());
        pSlider.setValue(h.getPriority());
    }

    protected void hIntervalLoad(Habit h) {
        ipAdapter.loadFromHabitInterval(h.getInterval());
    }

    protected void hTypeLoad(Habit h) {
        isBadType = h.getType() == HabitType.BAD;
        goodThb.setEnabled(isBadType);
        badThb.setEnabled(!isBadType);

        int pos;
        switch (h.getType()) {
            case GOOD_GOAL_COUNT:
                pos = 0;
                break;
            case GOOD_TASKS:
                pos = 2;
                break;
            default:
                pos = 1;
        }
        htPicker.postDelayed(() -> htPicker.setSelectedItemPosition(pos), 200);
    }

    protected void hGoalLoad(Habit h) {
        gcPicker.setMinValue(1);
        gcPicker.setMaxValue(5);
        gcPicker.setValue(h.getGoalCount());

        // Здесь можно добавить загрузку мини задач.
    }

    @Override
    protected void saveHabit() {
        HabitDTO habitData = saveDto();

        if (!checkDto(habitData)) return;

        HabitController.getInstance(requireContext()).change(habitId, habitData);
        exit();
    }
}