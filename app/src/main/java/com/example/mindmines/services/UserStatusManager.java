package com.example.mindmines.services;

import com.example.mindmines.models.UserStatus;
import com.example.mindmines.models.habits.Habit;

public class UserStatusManager {
    private static UserStatus status = new UserStatus();

    public static UserStatus getStatus() {
        return status;
    }

    public static void setStatus(UserStatus s) {
        status = s;
    }

    public static void gain(Habit h) {
        status.setExperience(status.getExperience() + 10);
    }
}
