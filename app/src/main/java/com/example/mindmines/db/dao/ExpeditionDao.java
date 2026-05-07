package com.example.mindmines.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.mindmines.db.entities.ExpeditionEntity;
import com.example.mindmines.db.entities.crossref.ExpeditionCharCrossRef;
import com.example.mindmines.db.entities.crossref.ExpeditionWithChars;

import java.util.List;

@Dao
public interface ExpeditionDao extends RepDao<ExpeditionEntity>{
    @Query("SELECT * FROM expeditions ORDER BY expeditionId ASC")
    List<ExpeditionEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ExpeditionEntity> expeditions);

    @Update
    void update(ExpeditionEntity expedition);

    @Query("DELETE FROM expeditions")
    void deleteAll();
}
