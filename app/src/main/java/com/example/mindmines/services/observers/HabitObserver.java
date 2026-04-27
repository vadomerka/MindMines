package com.example.mindmines.services.observers;

import com.example.mindmines.models.habits.Habit;

import java.util.List;

public interface HabitObserver extends RepositoryObserver<Habit> {
    void update(List<Habit> upd);
}
