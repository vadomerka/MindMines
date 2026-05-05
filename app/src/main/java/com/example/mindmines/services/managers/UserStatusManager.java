package com.example.mindmines.services.managers;

import android.content.Context;

import com.example.mindmines.models.user.UserStatus;
import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.services.factories.UserStatusFactory;
import com.example.mindmines.services.observers.UserStatusObserver;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.services.repositories.implementations.HabitRepository;
import com.example.mindmines.services.repositories.implementations.UserStatusRepository;

import java.util.ArrayList;
import java.util.List;

public class UserStatusManager {
    private final UserStatusRepository rep;
    private final Context context;

    public UserStatusManager(Context context) {
        this.context = context;
        rep = RepositoryService.getUserStatusRepository();
    }

    public UserStatus getStatus() {
        String userId = new AuthManager(context).getUserId();
        return rep.get(userId);
    }

    public void setStatus(UserStatus s) {
        rep.update(s);
    }

    public void removeStatus() {
        rep.remove(getStatus());
    }

    public void resetStatus() {
        String userId = new AuthManager(context).getUserId();
        rep.update(UserStatusFactory.getInstance().create(userId));
    }

    public void gain(Habit h) {
        UserStatus status = getStatus();
        Long newExp = XpManager.gainExp(status, h);
        XpManager.gainLevel(status, newExp);
        setStatus(status);
    }
}
