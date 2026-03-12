package com.example.mindmines.db.datasync;

import android.content.Context;
import android.util.Log;

import com.example.mindmines.db.dao.HabitDao;
import com.example.mindmines.db.HabitDatabase;
import com.example.mindmines.db.entities.HabitEntity;
import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.services.factories.HabitFactory;
import com.example.mindmines.services.repositories.HabitRepository;

import java.util.ArrayList;
import java.util.List;

public class HabitDataSynchronizer implements DataSynchronizer {
    private final HabitDao dao;

    public HabitDataSynchronizer(Context context) {
        HabitDatabase db = HabitDatabase.getInstance(context);
        this.dao = db.habitDao();
    }

    public void loadFromDB() {
        List<HabitEntity> entities = dao.getAll();
        List<Habit> habits = new ArrayList<>();

        for (HabitEntity e : entities) {
            habits.add(HabitFactory.createFromEntity(e));
        }

        HabitRepository.setAll(habits);
    }

    public void saveToDB() {
        List<Habit> habits = HabitRepository.getAll();
        List<HabitEntity> entities = new ArrayList<>();

        for (Habit h : habits) {
            entities.add(HabitFactory.createEntity(h));
        }

        dao.deleteAll();
        dao.insertAll(entities);
    }
}
