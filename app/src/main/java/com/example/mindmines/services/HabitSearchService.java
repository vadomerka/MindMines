package com.example.mindmines.services;

import com.example.mindmines.models.Habit;
import com.example.mindmines.services.repositories.HabitRepository;

public class HabitSearchService {
    public static Habit search(int hId) {
        return HabitRepository.get(hId);
    }
}
