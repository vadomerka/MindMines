package com.example.mindmines.services.managers;

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
        // Формула получения опыта.
        long baseChange = 10L;
        long streakExp = baseChange * h.getStreakNumber() * h.getPriority() * h.getDifficulty();
        // Добавляет штраф, только если
        long penaltyExp = baseChange * h.getPriority() / h.getDifficulty();
        if (h.getPenaltyNumber() == 0) { penaltyExp = 0L; }
        Long change = streakExp - penaltyExp;
        Long res = status.getExperience() + change;
        gainLevel(res, status);
        Log.d("Debug UserStatusManager", String.format("streak: %s; penalty: %s", streakExp, penaltyExp));
    }

    private static void gainLevel(Long exp, UserStatus status) {
        Long maxExp = status.getMaxExperience();
        if (exp < 0) {
            status.setExperience(0L);
        } else if (exp > maxExp) {
            if (status.getLevel() >= 30) { status.setLevel(30); }
            else { status.setLevel(status.getLevel() + 1); }
            status.setExperience(exp - maxExp);
        } else {
            status.setExperience(exp);
        }
    }

    public static void updateObservers() {
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
