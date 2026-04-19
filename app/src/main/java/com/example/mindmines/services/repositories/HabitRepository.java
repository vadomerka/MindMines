package com.example.mindmines.services.repositories;

import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.models.habits.HabitType;
import com.example.mindmines.views.observers.HabitObserver;
import com.example.mindmines.models.habits.HabitInterval;
import com.example.mindmines.models.habits.HabitTimeUnit;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class HabitRepository extends LocalRepository<Habit, HabitObserver> {
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

    public Habit get(Object id) {
        Optional<Habit> res = array.stream().filter(i -> i.getHabitId() == (int) id).findFirst();
        return res.orElse(null);
    }

    public void update(Habit item) {
        Habit found = get(item.getHabitId());
        array.set(array.indexOf(found), item);
        updateObservers();
    }
}
