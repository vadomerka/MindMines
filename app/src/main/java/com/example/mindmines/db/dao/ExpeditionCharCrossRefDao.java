package com.example.mindmines.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mindmines.db.entities.ExpeditionEntity;
import com.example.mindmines.db.entities.crossref.ExpeditionCharCrossRef;

import java.util.List;

@Dao
public interface ExpeditionCharCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ExpeditionCharCrossRef> crossRefs);

    @Query("SELECT * FROM expedition_char_cross_ref ORDER BY charId ASC")
    List<ExpeditionCharCrossRef> getAll();

    @Query("SELECT * FROM expedition_char_cross_ref WHERE charId = :charId")
    List<ExpeditionCharCrossRef> getByCharId(int charId);

    @Query("SELECT * FROM expedition_char_cross_ref WHERE expeditionId = :expeditionId")
    List<ExpeditionCharCrossRef> getByExpeditionId(int expeditionId);

    @Query("DELETE FROM expedition_char_cross_ref WHERE charId = :charId")
    void deleteByCharId(int charId);

    @Query("DELETE FROM expedition_char_cross_ref WHERE expeditionId = :expeditionId")
    void deleteByExpeditionId(int expeditionId);

    @Query("DELETE FROM expedition_char_cross_ref")
    void deleteAll();
}
