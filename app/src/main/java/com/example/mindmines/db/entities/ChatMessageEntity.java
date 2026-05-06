package com.example.mindmines.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import com.example.mindmines.services.repositories.DBEntity;

import java.time.OffsetDateTime;

@Entity(tableName = "messages")
public class ChatMessageEntity implements DBEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "messageId")
    public Integer messageId;

    @ColumnInfo(name = "userId")
    public String userId;
    @ColumnInfo(name = "author")
    public String author;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "context")
    public String context;

    @ColumnInfo(name = "body")
    public String body;

    @ColumnInfo(name = "creationTime")
    public OffsetDateTime creationTime;

    @ColumnInfo(name = "receivedTime")
    public OffsetDateTime receivedTime;

    public ChatMessageEntity() {
        messageId = 0;
    }

    public ChatMessageEntity(@NonNull Integer messageId,
                             String userId,
                             String author,
                             String type,
                             String context,
                             String body,
                             OffsetDateTime creationTime,
                             OffsetDateTime receivedTime) {
        this.messageId = messageId;
        this.userId = userId;
        this.author = author;
        this.type = type;
        this.context = context;
        this.body = body;
        this.creationTime = creationTime;
        this.receivedTime = receivedTime;
    }
}