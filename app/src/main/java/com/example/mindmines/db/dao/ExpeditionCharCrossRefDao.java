package com.example.mindmines.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mindmines.db.entities.ExpeditionEntity;
import com.example.mindmines.db.entities.HabitEntity;
import com.example.mindmines.db.entities.crossref.ExpeditionCharCrossRef;

import java.util.List;

@Dao
public interface ExpeditionCharCrossRefDao extends RepDao<ExpeditionCharCrossRef>{
    @Query("SELECT * FROM expedition_char_cross_ref ORDER BY charId ASC")
    List<ExpeditionCharCrossRef> getAll();

    @Query("SELECT expedition_char_cross_ref.* " +
            "FROM expedition_char_cross_ref " +
            "INNER JOIN characters ON expedition_char_cross_ref.charId = characters.charId " +
            "WHERE characters.userId = :userId")
    List<ExpeditionCharCrossRef> getAllByUserId(String userId);

    @Query("SELECT * FROM expedition_char_cross_ref WHERE charId = :charId")
    List<ExpeditionCharCrossRef> getByCharId(int charId);

    @Query("SELECT * FROM expedition_char_cross_ref WHERE expeditionId = :expeditionId")
    List<ExpeditionCharCrossRef> getByExpeditionId(int expeditionId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ExpeditionCharCrossRef> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ExpeditionCharCrossRef entity);

    @Query("DELETE FROM expedition_char_cross_ref WHERE charId = :charId")
    void deleteByCharId(int charId);

    @Query("DELETE FROM expedition_char_cross_ref WHERE expeditionId = :expeditionId")
    void deleteByExpeditionId(int expeditionId);

    @Query("DELETE FROM expedition_char_cross_ref")
    void deleteAll();

    @Delete
    void delete(ExpeditionCharCrossRef entity);

    @Update
    void update(ExpeditionCharCrossRef entity);
}
