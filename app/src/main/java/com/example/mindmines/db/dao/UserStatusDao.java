package com.example.mindmines.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mindmines.db.entities.UserStatusEntity;

import java.util.List;

@Dao
public interface UserStatusDao extends RepDao<UserStatusEntity> {
    @Query("SELECT * FROM userStatuses ORDER BY userId ASC")
    List<UserStatusEntity> getAll();

    @Query("SELECT * FROM userStatuses WHERE userId = :userId ORDER BY userId ASC")
    List<UserStatusEntity> getAllByUserId(String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<UserStatusEntity> userStatuses);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserStatusEntity entity);

    @Update
    void update(UserStatusEntity userStatuses);

    @Query("DELETE FROM userStatuses")
    void deleteAll();

    @Delete
    void delete(UserStatusEntity entity);

    @Query("DELETE FROM userStatuses WHERE userId = :userId")
    void deleteAllByUserId(String userId);
}
