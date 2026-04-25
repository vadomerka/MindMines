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

    public static void update(Habit h) {
        HabitRepository rep = RepositoryService.getHabitRepository();
        rep.update(h);
    }
}
