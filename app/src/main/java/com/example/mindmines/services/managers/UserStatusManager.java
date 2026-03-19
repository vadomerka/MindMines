package com.example.mindmines.services.managers;

import android.util.Log;

import com.example.mindmines.models.UserStatus;
import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.views.observers.UserStatusObserver;

import java.util.ArrayList;
import java.util.List;

public class UserStatusManager {
    private static UserStatus status = new UserStatus();
    private static final List<UserStatusObserver> observers = new ArrayList<>();

    public static UserStatus getStatus() {
        return status;
    }

    public static void setStatus(UserStatus s) {
        status = s;
    }

    public static void resetStatus() {
        status = new UserStatus();
    }

    public static void gain(Habit h) {
        // Формула получения опыта.
        long baseExpChange = 10L;
        long streakExp = baseExpChange * h.getStreakNumber() * h.getPriority() * h.getDifficulty();
        // Добавляет штраф, только если
        long penaltyExp = baseExpChange * h.getPriority() / h.getDifficulty();
        if (h.getPenaltyNumber() == 0) { penaltyExp = 0L; }
        Long change = streakExp - penaltyExp;
        Long res = status.getExperience() + change;
        gainLevel(res, status);
        Log.d("Debug UserStatusManager", String.format("streak: %s; penalty: %s", streakExp, penaltyExp));
    }

    private static void gainLevel(Long exp, UserStatus status) {
        int baseMaxExpChange = 10;
        double maxExpKoef = 1.25;
        Integer level = status.getLevel();
        Long maxExp = status.getMaxExperience();
        if (exp < 0) {
            status.setExperience(0L);
            return;
        }
        while (exp > maxExp) {
            exp -= maxExp;
            status.setExperience(exp);
            if (level >= 30) { level = 30; }
            else {
                level++;
                maxExp = (long)(baseMaxExpChange * level * maxExpKoef);
            }
        }
        status.setMaxExperience(maxExp);
        status.setLevel(level);
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
