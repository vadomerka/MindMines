package com.example.mindmines.db;

import android.content.Context;

import com.example.mindmines.db.entities.HabitEntity;
import com.example.mindmines.models.Habit;
import com.example.mindmines.services.factories.HabitFactory;
import com.example.mindmines.services.repositories.HabitRepository;

import java.util.ArrayList;
import java.util.List;

public class HabitDataSynchronizer {
    private final HabitDao dao;

    public HabitDataSynchronizer(Context context) {
        HabitDatabase db = HabitDatabase.getInstance(context);
        this.dao = db.habitDao();
    }

    public void loadIntoRepository() {
//        Log.d(TAG, "loadIntoRepository: loaded");
        List<HabitEntity> entities = dao.getAll();
        List<Habit> habits = new ArrayList<>();

        for (HabitEntity e : entities) {
            habits.add(HabitFactory.createFromEntity(e));
        }

        HabitRepository.setAll(habits);
    }

    public void saveFromRepository() {
//        Log.d(TAG, "saveFromRepository: saved");
        List<Habit> habits = HabitRepository.getAll();
        List<HabitEntity> entities = new ArrayList<>();

        for (Habit h : habits) {
            entities.add(HabitFactory.createEntity(h));
        }

        dao.deleteAll();
        dao.insertAll(entities);
    }
}
