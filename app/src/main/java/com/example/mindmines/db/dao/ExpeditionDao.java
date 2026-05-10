package com.example.mindmines.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mindmines.db.entities.ExpeditionEntity;

import java.util.List;

@Dao
public interface ExpeditionDao extends RepDao<ExpeditionEntity> {
    @Query("SELECT * FROM expeditions ORDER BY expeditionId ASC")
    List<ExpeditionEntity> getAll();

    @Query("SELECT * FROM expeditions WHERE userId = :userId ORDER BY expeditionId ASC")
    List<ExpeditionEntity> getAllByUserId(String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ExpeditionEntity> expeditions);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ExpeditionEntity expedition);

    @Update
    void update(ExpeditionEntity expedition);

    @Query("DELETE FROM expeditions")
    void deleteAll();

    @Delete
    void delete(ExpeditionEntity expedition);

    @Query("DELETE FROM expeditions WHERE userId = :userId")
    void deleteAllByUserId(String userId);
}
