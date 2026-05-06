package com.example.mindmines.services.repositories.implementations;

import android.util.Log;

import com.example.mindmines.db.MindMinesDatabase;
import com.example.mindmines.db.entities.UserStatusEntity;
import com.example.mindmines.models.user.UserStatus;
import com.example.mindmines.services.observers.UserStatusObserver;
import com.example.mindmines.services.repositories.LocalObservedRepository;

import java.util.List;

public class UserStatusRepository extends LocalObservedRepository<String, UserStatus, UserStatusObserver> {
    @Override
    public void updateObservers() {
        super.updateObservers();
        List<UserStatusEntity> all = MindMinesDatabase.getInstance(context).userStatusDao().getAll();
        Log.d("Debug UserStatus", "start");
        for (UserStatusEntity us: all) {
            Log.d("Debug UserStatus", String.format("%s, %d", us.userId, us.level));
        }
    }
}
