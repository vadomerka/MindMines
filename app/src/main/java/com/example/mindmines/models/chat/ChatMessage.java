package com.example.mindmines.models.chat;

import com.example.mindmines.models.interfaces.RepositoryItem;

import java.time.OffsetDateTime;

public class ChatMessage implements RepositoryItem<Integer> {
    private Integer messageId;
    private String userId;
    private String author;
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


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String value) {
        this.author = author;
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
                       String userId,
                       String author,
                       String type,
                       String context,
                       String body,
                       OffsetDateTime creationTime,
                       OffsetDateTime receivedTime
    ) {
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
