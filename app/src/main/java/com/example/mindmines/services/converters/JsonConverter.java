package com.example.mindmines.services.converters;

import com.example.mindmines.models.Habit;
import com.example.mindmines.models.enums.HabitType;
import com.google.gson.Gson;

public class JsonConverter {
    private static final Gson gson = new Gson();

    public static String toJson(Habit habit) {
        return gson.toJson(habit);
    }

    public static String example() {
        Habit habit = new Habit(
                1,
                1,
                HabitType.GOOD_INTERVAL,
                "title",
                "desc",
                1,
                1,
                1,
                1,
                null,
                null,
                null,
                null
        );
        return toJson(habit);
    }
}
