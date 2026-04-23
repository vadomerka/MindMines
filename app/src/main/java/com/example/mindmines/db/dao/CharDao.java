package com.example.mindmines.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.mindmines.db.entities.CharEntity;
import com.example.mindmines.db.entities.ExpeditionEntity;
import com.example.mindmines.db.entities.crossref.CharWithExpeditions;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface CharDao {
    @Query("SELECT * FROM characters ORDER BY charId ASC")
    List<CharEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CharEntity> characters);

    @Update
    void update(CharEntity character);

    @Query("DELETE FROM characters")
    void deleteAll();


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
