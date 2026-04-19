package com.example.mindmines.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mindmines.db.entities.ExpeditionEntity;

import java.util.List;

@Dao
public interface ExpeditionDao {
    @Query("SELECT * FROM expeditions ORDER BY expeditionId ASC")
    List<ExpeditionEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ExpeditionEntity> expeditions);

    @Update
    void update(ExpeditionEntity expedition);

    @Query("DELETE FROM expeditions")
    void deleteAll();
}
