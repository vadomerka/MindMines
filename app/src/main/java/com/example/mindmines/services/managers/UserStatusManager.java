package com.example.mindmines.services.managers;

import com.example.mindmines.models.user.UserStatus;
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
        Long newExp = ExpManager.gainExp(status, h);
        ExpManager.gainLevel(status, newExp);
    }

    public static void updateObservers() {
        for (UserStatusObserver observer: observers) {
            observer.update(new ArrayList<>());
        }
    }

    public static void subscribe(UserStatusObserver observer) {
        observers.add(observer);
    }

    public static void unsubscribe(UserStatusObserver observer) {
        observers.remove(observer);
    }
}
