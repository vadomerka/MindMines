package com.example.mindmines.db.datasync;

import android.content.Context;
import android.util.Log;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

import com.example.mindmines.db.MindMinesDatabase;
import com.example.mindmines.db.dao.HabitDao;
import com.example.mindmines.db.dao.UserStatusDao;
import com.example.mindmines.db.entities.HabitEntity;
import com.example.mindmines.db.entities.UserStatusEntity;
import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.models.user.UserStatus;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.services.factories.HabitFactory;
import com.example.mindmines.services.factories.UserStatusFactory;
import com.example.mindmines.services.managers.UserStatusManager;
import com.example.mindmines.services.observers.UserStatusObserver;
import com.example.mindmines.services.repositories.RepositoryService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

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
            uses.add(UserStatusFactory.getInstance().createFromEntity(e));
        }

        RepositoryService.getUserStatusRepository().setAll(uses);
    }

    public void saveToDB() {
        List<UserStatus> uses = RepositoryService.getUserStatusRepository().getAll();
        List<UserStatusEntity> entities = new ArrayList<>();

        for (UserStatus h : uses) {
            entities.add(UserStatusFactory.getInstance().createEntity(h));
        }

        String userId = new AuthManager(context).getUserId();
        dao.deleteAllByUserId(userId);
        dao.insertAll(entities);
    }

    @Override
    public void update(List<UserStatus> upd) {
        saveToDB();
    }
}
