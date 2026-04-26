package com.example.mindmines.models.chat;

import com.example.mindmines.services.repositories.RepositoryItem;

import java.time.OffsetDateTime;

public class ChatMessage implements RepositoryItem<Integer> {
    private Integer messageId;
    private Integer userId;
    private String type;
    private String context;
    private String body;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
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

    public ChatMessage(Integer messageId,
                       Integer userId,
                       String type,
                       String context,
                       String body,
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
