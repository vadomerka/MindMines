package com.example.mindmines.services.converters;

import com.example.mindmines.models.Habit;
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
                "title",
                "desc",
                null,
                1.0F,
                1,
                1,
                "type 1",
                null,
                1,
                1
        );
        return toJson(habit);
    }
}
