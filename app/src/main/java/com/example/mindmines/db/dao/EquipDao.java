package com.example.mindmines.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mindmines.db.entities.EquipEntity;

import java.util.List;

@Dao
public interface EquipDao extends RepDao<EquipEntity>{
    @Query("SELECT * FROM equipment ORDER BY equipId ASC")
    List<EquipEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<EquipEntity> equipment);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EquipEntity entity);

    @Update
    void update(EquipEntity character);

    @Query("DELETE FROM equipment")
    void deleteAll();

    @Delete
    void delete(EquipEntity entity);
}
