package com.example.mindmines.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.mindmines.data.entities.CharEntity;
import com.example.mindmines.data.entities.ExpeditionEntity;
import com.example.mindmines.data.entities.crossref.CharWithExpeditions;

import java.util.List;

@Dao
public interface CharDao extends RepDao<CharEntity> {
    @Query("SELECT * FROM characters ORDER BY charId ASC")
    List<CharEntity> getAll();

    @Query("SELECT * FROM characters WHERE userId = :userId ORDER BY charId ASC")
    List<CharEntity> getAllByUserId(String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CharEntity> characters);

    @Insert
    void insert(CharEntity entity);

    @Update
    void update(CharEntity character);

    @Query("DELETE FROM characters")
    void deleteAll();

    @Delete
    void delete(CharEntity entity);

    @Transaction
    @Query("SELECT * FROM characters WHERE charId = :charId")
    CharWithExpeditions getCharWithExpeditions(int charId);

    @Query("SELECT e.* FROM expeditions e " +
            "INNER JOIN expedition_char_cross_ref ref ON e.expeditionId = ref.expeditionId " +
            "WHERE ref.charId = :charId")
    List<ExpeditionEntity> getExpeditionsForCharacter(int charId);

    @Transaction
    @Query("SELECT * FROM characters")
    List<CharWithExpeditions> getAllCharsWithExpeditions();
}
