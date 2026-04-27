package com.example.mindmines.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mindmines.db.entities.MessageEntity;

import java.util.List;

@Dao
public interface MessageDao {

    @Query("SELECT * FROM messages ORDER BY messageId ASC")
    List<MessageEntity> getAll();

    @Query("SELECT * FROM messages WHERE messageId = :id")
    MessageEntity getById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MessageEntity> messages);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MessageEntity message);

    @Update
    void update(MessageEntity message);

    @Query("DELETE FROM messages")
    void deleteAll();

    @Query("DELETE FROM messages WHERE messageId = :id")
    void deleteById(int id);

    // Дополнительные выборки, если нужны
    @Query("SELECT * FROM messages WHERE author = :author ORDER BY creationTime ASC")
    List<MessageEntity> getByAuthor(String author);
}
