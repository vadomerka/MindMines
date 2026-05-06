package com.example.mindmines.db.datasync;

import android.content.Context;
import android.util.Log;

import com.example.mindmines.db.dao.HabitDao;
import com.example.mindmines.db.MindMinesDatabase;
import com.example.mindmines.db.entities.HabitEntity;
import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.services.factories.HabitFactory;
import com.example.mindmines.services.repositories.RepositoryService;

import java.util.ArrayList;
import java.util.List;

public class HabitDataSynchronizer implements DataSynchronizer {
    private final Context context;
    private final HabitDao dao;

    public HabitDataSynchronizer(Context context) {
        this.context = context;
        MindMinesDatabase db = MindMinesDatabase.getInstance(context);
        this.dao = db.habitDao();
    }

    public void loadFromDB() {
        String userId = new AuthManager(context).getUserId();
        List<HabitEntity> entities = dao.getAllByUserId(userId);

        List<Habit> habits = new ArrayList<>();

        for (HabitEntity e: dao.getAll()) {
            Log.d("Debug HabitSync load", "loadFromDB: " + e.title + " " + e.habitId);
        }

        for (HabitEntity e : entities) {
            Habit res = HabitFactory.getInstance().createFromEntity(e);
            habits.add(res);
        }

        RepositoryService.getHabitRepository().setAll(habits);
    }

    public void saveToDB() {
        List<Habit> habits = RepositoryService.getHabitRepository().getAll();
        List<HabitEntity> entities = new ArrayList<>();

        for (Habit h : habits) {
            entities.add(HabitFactory.getInstance().createEntity(h));
        }

        String userId = new AuthManager(context).getUserId();
        dao.deleteAllByUserId(userId);
        dao.insertAll(entities);

        for (HabitEntity e: dao.getAll()) {
            Log.d("Debug HabitSync save", "saveToDB: " + e.title + " " + e.habitId);
        }
    }
}
