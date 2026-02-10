package com.example.mindmines.services.repositories;

import com.example.mindmines.models.Habit;
import com.example.mindmines.models.enums.HabitType;
import com.example.mindmines.views.habit.HabitInterval;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HabitRepository {
    private static List<Habit> array;

    public static void init() {
        array = new ArrayList<Habit>() {
            {
                add(new Habit(1, 1, HabitType.GOOD_INTERVAL,"title", "desc", 1,1, 1, 1,  null, null, null, null));
                add(new Habit(2, 1, HabitType.GOOD_INTERVAL,"title2", "desc2", 1,1, 1, 1,  null, null, null, null));
                add(new Habit(3, 1, HabitType.GOOD_INTERVAL,"title3", "desc3", 1,1, 1, 1,  null, null, null, null));
            }
        };
    }

    public static List<Habit> getAll() {
        return array;
    }

    public static void add(Habit item) {
        array.add(item);
    }

    public static void remove(Habit item) {
        array.remove(item);
    }

    public static Habit get(int itemId) {
        Optional<Habit> res = array.stream().filter(i -> i.getHabitId() == itemId).findFirst();
        return res.orElse(null);
    }

    public static void update(Habit item) {
        Habit found = get(item.getHabitId());
        array.set(array.indexOf(found), item);
    }
}
