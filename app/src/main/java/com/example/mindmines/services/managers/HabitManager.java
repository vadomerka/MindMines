package com.example.mindmines.services.managers;

import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.models.enums.HabitType;
import com.example.mindmines.services.repositories.HabitRepository;

public class HabitManager {
    public static void add(Habit h) {
        HabitRepository.add(h);
    }

    public static void change(
            Integer hId,
            String title,
            String desc,
            Float frequency,
            Integer priority,
            Integer difficulty,
            HabitType type
    ) {
        Habit h = HabitRepository.get(hId);

        h.setTitle(title);
        h.setDescription(desc);
//        h.setCheckingFrequency(frequency);
        h.setPriority(priority);
        h.setDifficulty(difficulty);
        h.setType(type);

        HabitRepository.update(h);
    }

    public static void update(Habit h) {
        HabitRepository.update(h);
    }
}
