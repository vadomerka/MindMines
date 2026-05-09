package com.example.mindmines.services.repositories.dao;

import android.util.Log;

import com.example.mindmines.db.MindMinesDatabase;
import com.example.mindmines.db.entities.UserStatusEntity;
import com.example.mindmines.models.user.UserStatus;
import com.example.mindmines.services.converters.entities.UserStatusConverter;
import com.example.mindmines.services.observers.UserStatusObserver;
import com.example.mindmines.services.repositories.LocalDaoRepository;

import java.util.List;

public class UserStatusRepository extends LocalDaoRepository<String, UserStatus, UserStatusEntity, UserStatusObserver> {
    @Override
    public void initDao() {
        MindMinesDatabase db = MindMinesDatabase.getInstance(context);
        this.dao = db.userStatusDao();
    }

    @Override
    public void initConverter() {
        converter = new UserStatusConverter();
    }

    @Override
    public void updateObservers() {
        super.updateObservers();
        List<UserStatusEntity> all = MindMinesDatabase.getInstance(context).userStatusDao().getAll();
        Log.d("Debug UserStatus", "start");
        for (UserStatusEntity us: all) {
            Log.d("Debug UserStatus", String.format("%s, %d", us.userId, us.level));
        }
    }

    protected String defaultId() { return ""; }
}
