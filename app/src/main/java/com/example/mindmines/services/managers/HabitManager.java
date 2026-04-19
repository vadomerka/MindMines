package com.example.mindmines.services.managers;

import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.models.habits.HabitType;
import com.example.mindmines.services.repositories.HabitRepository;
import com.example.mindmines.services.repositories.RepositoryService;

public class HabitManager {
    public static void add(Habit h) {
        HabitRepository rep = RepositoryService.getHabitRepository();
        rep.add(h);
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
        HabitRepository rep = RepositoryService.getHabitRepository();

        Habit h = rep.get(hId);

        h.setTitle(title);
        h.setDescription(desc);
        h.setPriority(priority);
        h.setDifficulty(difficulty);
        h.setType(type);

        rep.update(h);
    }

    public static void update(Habit h) {
        HabitRepository rep = RepositoryService.getHabitRepository();
        rep.update(h);
    }
}
