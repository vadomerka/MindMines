package com.example.mindmines.services.managers;

import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.services.repositories.dao.HabitRepository;

public class HabitManager {
    public static void add(Habit h) {
        HabitRepository rep = RepositoryService.getHabitRepository();
        rep.add(h);
    }

    public static void update(Habit h) {
        HabitRepository rep = RepositoryService.getHabitRepository();
        rep.update(h);
    }

    public static void delete(int hId) {
        HabitRepository rep = RepositoryService.getHabitRepository();
        rep.remove(rep.get(hId));
    }
}
