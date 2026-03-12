package com.example.mindmines.models.habits;

public class HabitInterval {
    private final int number;
    private final HabitTimeUnit unit;

    public HabitInterval(int number, HabitTimeUnit unit) {
        this.number = number;
        this.unit = unit;
    }

    public int getNumber() { return number; }

    public HabitTimeUnit getTimeUnit() { return unit; }
}
