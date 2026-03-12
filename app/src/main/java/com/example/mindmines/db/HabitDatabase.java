package com.example.mindmines.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.mindmines.db.dao.HabitDao;
import com.example.mindmines.db.entities.HabitEntity;

@Database(entities = {HabitEntity.class}, version = 1, exportSchema = false)
@TypeConverters(HabitTypeConverter.class)
public abstract class HabitDatabase extends RoomDatabase {
    private static volatile HabitDatabase INSTANCE;

    public abstract HabitDao habitDao();

    public static HabitDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (HabitDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            HabitDatabase.class,
                            "habits.db"
                    )
                            .allowMainThreadQueries()  // not recommended
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
