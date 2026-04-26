package com.example.mindmines.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.mindmines.services.repositories.RepositoryItem;

import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;

public class MessageEntity implements RepositoryItem<Integer> {
    private Integer messageId;
    private Integer userId;
    private String type;
    private String context;
    private Integer body;
    private OffsetDateTime creationTime;
    private OffsetDateTime receivedTime;

    public Integer getId() {
        return messageId;
    }

    public void setId(Integer value) {
        this.messageId = value;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer value) {
        this.userId = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Integer getBody() {
        return body;
    }

    public void setBody(Integer body) {
        this.body = body;
    }

    public OffsetDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(OffsetDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public OffsetDateTime getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(OffsetDateTime receivedTime) {
        this.receivedTime = receivedTime;
    }

    public MessageEntity(Integer messageId,
                         Integer userId,
                         String type,
                         String context,
                         Integer body,
                         OffsetDateTime creationTime,
                         OffsetDateTime receivedTime
    ) {
        this.messageId = messageId;
        this.userId = userId;
        this.type = type;
        this.context = context;
        this.body = body;
        this.creationTime = creationTime;
        this.receivedTime = receivedTime;
    }
}