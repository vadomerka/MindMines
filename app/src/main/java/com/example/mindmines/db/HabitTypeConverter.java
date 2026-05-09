package com.example.mindmines.db;

import androidx.room.TypeConverter;

import java.time.OffsetDateTime;

public class HabitTypeConverter {
    @TypeConverter
    public static OffsetDateTime fromString(String value) {
        return value == null ? null : OffsetDateTime.parse(value);
    }

    @TypeConverter
    public static String offSetDateToString(OffsetDateTime date) {
        return date == null ? null : date.toString();
    }
}
