package com.example.mindmines.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mindmines.data.entities.ChatMessageEntity;

import java.util.List;

@Dao
public interface MessageDao extends RepDao<ChatMessageEntity> {

    @Query("SELECT * FROM messages ORDER BY messageId ASC")
    List<ChatMessageEntity> getAll();

    @Query("SELECT * FROM messages WHERE messageId = :id")
    ChatMessageEntity getById(int id);

    @Query("SELECT * FROM messages WHERE userId = :userId ORDER BY messageId ASC")
    List<ChatMessageEntity> getAllByUserId(String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ChatMessageEntity> messages);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ChatMessageEntity message);

    @Update
    void update(ChatMessageEntity message);

    @Query("DELETE FROM messages")
    void deleteAll();

    @Query("DELETE FROM messages WHERE messageId = :id")
    void deleteById(int id);

    @Delete
    void delete(ChatMessageEntity message);

    @Query("SELECT * FROM messages WHERE author = :author ORDER BY creationTime ASC")
    List<ChatMessageEntity> getByAuthor(String author);
}
