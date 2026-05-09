package com.example.mindmines.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mindmines.db.entities.CharEntity;
import com.example.mindmines.db.entities.HabitEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface HabitDao extends RepDao<HabitEntity> {
    @Query("SELECT * FROM habits ORDER BY habitId ASC")
    List<HabitEntity> getAll();

    @Query("SELECT * FROM habits WHERE userId = :userId ORDER BY habitId ASC")
    List<HabitEntity> getAllByUserId(String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<HabitEntity> habits);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(HabitEntity habit);

    @Update
    void update(HabitEntity habit);

    @Query("DELETE FROM habits")
    void deleteAll();

    @Delete
    void delete(HabitEntity habit);

    @Query("DELETE FROM habits WHERE userId = :userId")
    void deleteAllByUserId(String userId);
}
