package com.example.mindmines.services.repositories;

import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.models.habits.HabitType;
import com.example.mindmines.services.observers.HabitObserver;
import com.example.mindmines.models.habits.HabitInterval;
import com.example.mindmines.models.habits.HabitTimeUnit;

import java.time.OffsetDateTime;
import java.util.ArrayList;

public class HabitRepository extends LocalObservedRepository<Integer, Habit, HabitObserver> {
    @Override
    public void initArray() {
        OffsetDateTime n = OffsetDateTime.now();
        array = new ArrayList<Habit>() {
            {
                add(new Habit(1, 1, HabitType.GOOD_INTERVAL,"title", "desc", 1, 1,1, 1, 3, n, n, n, new HabitInterval(1, HabitTimeUnit.MINUTE)));
                add(new Habit(2, 1, HabitType.GOOD_INTERVAL,"title2", "desc2", 1, 1,1, 2, 2,  n, n, n, new HabitInterval(2, HabitTimeUnit.MINUTE)));
//                add(new Habit(3, 1, HabitType.GOOD_INTERVAL,"title3", "desc3", 1,1, 3, 1,  n, n, n, new HabitInterval(3, HabitTimeUnit.MINUTE)));
            }
        };
    }
}
