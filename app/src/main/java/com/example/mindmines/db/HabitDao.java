package com.example.mindmines.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mindmines.db.entities.HabitEntity;

import java.util.List;

@Dao
public interface HabitDao {
    @Query("SELECT * FROM habits ORDER BY habitId ASC")
    List<HabitEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<HabitEntity> habits);

    @Update
    void update(HabitEntity habit);

    @Query("DELETE FROM habits")
    void deleteAll();
}
