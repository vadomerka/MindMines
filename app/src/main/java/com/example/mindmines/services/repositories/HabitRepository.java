package com.example.mindmines.services.repositories;

import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.models.enums.HabitType;
import com.example.mindmines.views.observers.HabitObserver;
import com.example.mindmines.models.habits.HabitInterval;
import com.example.mindmines.models.habits.HabitTimeUnit;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HabitRepository {
    private static final String TAG = "Debug data sync";
    private static List<Habit> array;
    private static List<HabitObserver> observers;

    public static void init() {
        observers = new ArrayList<>();
        OffsetDateTime n = OffsetDateTime.now();
        array = new ArrayList<Habit>() {
            {
                add(new Habit(1, 1, HabitType.GOOD_INTERVAL,"title", "desc", 1,1, 1, 3, n, n, n, new HabitInterval(1, HabitTimeUnit.MINUTE)));
                add(new Habit(2, 1, HabitType.GOOD_INTERVAL,"title2", "desc2", 1,1, 2, 2,  n, n, n, new HabitInterval(2, HabitTimeUnit.MINUTE)));
                add(new Habit(3, 1, HabitType.GOOD_INTERVAL,"title3", "desc3", 1,1, 3, 1,  n, n, n, new HabitInterval(3, HabitTimeUnit.MINUTE)));
            }
        };
    }

    public static void subscribe(HabitObserver o) {
        observers.add(o);
    }

    public static void unsubscribe(HabitObserver o) {
        observers.remove(o);
    }

    public static List<Habit> getAll() {
        return array;
    }

    public static void setAll(List<Habit> narr) {
        array = narr;
        updateObservers();
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
        updateObservers();
    }

    public static void updateObservers() {
        for (HabitObserver o: observers) {
            o.updateHabits();
        }
    }
}
