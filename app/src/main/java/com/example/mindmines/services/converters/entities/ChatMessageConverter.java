package com.example.mindmines.services.converters.entities;

import com.example.mindmines.data.entities.ChatMessageEntity;
import com.example.mindmines.models.chat.ChatMessage;

public class ChatMessageConverter implements RepConverter<Integer, ChatMessage, ChatMessageEntity> {
    public ChatMessage toItem(ChatMessageEntity entity) {

        return new ChatMessage(
                entity.messageId,
                entity.userId,
                entity.author,
                entity.type,
                entity.context,
                entity.body,
                entity.creationTime,
                entity.receivedTime
        );
    }

    public ChatMessageEntity toEntity(ChatMessage item) {

        return new ChatMessageEntity(
                item.getId(),
                item.getUserId(),
                item.getAuthor(),
                item.getType(),
                item.getContext(),
                item.getBody(),
                item.getCreationTime(),
                item.getReceivedTime()
        );
    }
}
