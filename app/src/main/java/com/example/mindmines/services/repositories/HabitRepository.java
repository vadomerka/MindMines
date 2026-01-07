package com.example.mindmines.services.repositories;

import com.example.mindmines.models.Habit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HabitRepository {
    private static List<Habit> array;

    public static void init() {
        array = new ArrayList<Habit>() {
            {
                add(new Habit(1, 1, "title", "desc", null, 1.0F, 1, 1, "type", null, 0, 0));
                add(new Habit(2, 1, "title2", "desc2", null, 1.0F, 1, 1, "type", null, 0, 0));
                add(new Habit(3, 1, "title3", "desc3", null, 1.0F, 1, 1, "type", null, 0, 0));
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
}
