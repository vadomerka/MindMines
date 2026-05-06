package com.example.mindmines.db.datasync;

import android.content.Context;

import com.example.mindmines.db.MindMinesDatabase;
import com.example.mindmines.db.dao.UserStatusDao;
import com.example.mindmines.db.entities.UserStatusEntity;
import com.example.mindmines.models.user.UserStatus;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.services.factories.UserStatusFactory;
import com.example.mindmines.services.observers.UserStatusObserver;
import com.example.mindmines.services.repositories.RepositoryService;

import java.util.ArrayList;
import java.util.List;

public class UserStatusSynchronizer implements DataSynchronizer, UserStatusObserver {
    private final Context context;
    private final UserStatusDao dao;

    public UserStatusSynchronizer(Context context) {
        this.context = context;
        MindMinesDatabase db = MindMinesDatabase.getInstance(context);
        this.dao = db.userStatusDao();
    }


    public void loadFromDB() {
        String userId = new AuthManager(context).getUserId();
        List<UserStatusEntity> entities = dao.getAllByUserId(userId);
        List<UserStatus> uses = new ArrayList<>();

        for (UserStatusEntity e : entities) {
            uses.add(UserStatusFactory.getInstance().toItem(e));
        }

//        RepositoryService.getUserStatusRepository().setAll(uses);
    }

    public void saveToDB() {
        List<UserStatus> uses = RepositoryService.getUserStatusRepository().getAll();
        List<UserStatusEntity> entities = new ArrayList<>();

        for (UserStatus h : uses) {
            entities.add(UserStatusFactory.getInstance().toEntity(h));
        }

        String userId = new AuthManager(context).getUserId();
//        dao.deleteAllByUserId(userId);
//        dao.insertAll(entities);
    }

    @Override
    public void update(List<UserStatus> upd) {
        saveToDB();
    }
}
