package com.example.mindmines.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import java.time.OffsetDateTime;

@Entity(tableName = "messages")
public class MessageEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "messageId")
    public Integer messageId;

    @ColumnInfo(name = "author")
    public String author;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "context")
    public String context;

    @ColumnInfo(name = "body")
    public Integer body;

    @ColumnInfo(name = "creationTime")
    public OffsetDateTime creationTime;

    @ColumnInfo(name = "receivedTime")
    public OffsetDateTime receivedTime;

    public MessageEntity() {
        messageId = 0;
    }

    public MessageEntity(@NonNull Integer messageId,
                         String author,
                         String type,
                         String context,
                         Integer body,
                         OffsetDateTime creationTime,
                         OffsetDateTime receivedTime) {
        this.messageId = messageId;
        this.author = author;
        this.type = type;
        this.context = context;
        this.body = body;
        this.creationTime = creationTime;
        this.receivedTime = receivedTime;
    }
}