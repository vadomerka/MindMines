package com.example.mindmines.services;

import android.util.Log;

import com.example.mindmines.models.UserStatus;
import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.views.observers.UserStatusObserver;

import java.util.ArrayList;
import java.util.List;

public class UserStatusManager {
    private static UserStatus status = new UserStatus();
    private static List<UserStatusObserver> observers = new ArrayList<>();

    public static UserStatus getStatus() {
        return status;
    }

    public static void setStatus(UserStatus s) {
        status = s;
    }

    public static void gain(Habit h) {
        Long baseChange = 10L;
        Long streakExp = baseChange * h.getStreakNumber() * h.getPriority() * h.getDifficulty();
        Long penaltyExp = baseChange * h.getPenaltyNumber() * h.getPriority() / h.getDifficulty();
        Long change = streakExp - penaltyExp;
        status.setExperience(status.getExperience() + change);
        Log.d("Debug UserStatusManager", String.format("streak: %s; penalty: %s", streakExp, penaltyExp));

        updateObservers();
    }

    private static void updateObservers() {
        for (UserStatusObserver observer: observers) {
            observer.updateUserStatus();
        }
    }

    public static void subscribe(UserStatusObserver observer) {
        observers.add(observer);
    }

    public static void unsubscribe(UserStatusObserver observer) {
        observers.remove(observer);
    }
}
