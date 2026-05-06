package com.example.mindmines.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mindmines.db.entities.ChatMessageEntity;

import java.util.List;

@Dao
public interface MessageDao {

    @Query("SELECT * FROM ChatMessageEntity ORDER BY messageId ASC")
    List<ChatMessageEntity> getAll();

    @Query("SELECT * FROM ChatMessageEntity WHERE messageId = :id")
    ChatMessageEntity getById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ChatMessageEntity> messages);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ChatMessageEntity message);

    @Update
    void update(ChatMessageEntity message);

    @Query("DELETE FROM ChatMessageEntity")
    void deleteAll();

    @Query("DELETE FROM ChatMessageEntity WHERE messageId = :id")
    void deleteById(int id);

    // Дополнительные выборки, если нужны
    @Query("SELECT * FROM ChatMessageEntity WHERE author = :author ORDER BY creationTime ASC")
    List<ChatMessageEntity> getByAuthor(String author);
}
