package com.example.mindmines.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mindmines.db.entities.HabitEntity;
import com.example.mindmines.db.entities.UserStatusEntity;

import java.util.List;

@Dao
public interface UserStatusDao {
    @Query("SELECT * FROM userStatuses ORDER BY userId ASC")
    List<UserStatusEntity> getAll();

    @Query("SELECT * FROM userStatuses WHERE userId = :userId ORDER BY userId ASC")
    List<UserStatusEntity> getAllByUserId(String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<UserStatusEntity> userStatuses);

    @Update
    void update(UserStatusEntity userStatuses);

    @Query("DELETE FROM userStatuses")
    void deleteAll();

    @Query("DELETE FROM userStatuses WHERE userId = :userId")
    void deleteAllByUserId(String userId);
}
