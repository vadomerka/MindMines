package com.example.mindmines.services.factories;

import com.example.mindmines.db.entities.ChatMessageEntity;
import com.example.mindmines.models.chat.ChatMessage;
import com.example.mindmines.services.converters.entities.RepConverter;
import com.example.mindmines.services.repositories.dao.ChatMessageRepository;
import com.example.mindmines.services.repositories.RepositoryService;

import java.time.OffsetDateTime;

public class ChatMessageFactory implements RepConverter<Integer, ChatMessage, ChatMessageEntity> {
    private final ChatMessageRepository rep;
    private static ChatMessageFactory instance;

    public ChatMessageFactory() {
        this.rep = RepositoryService.getChatMessageRepository();
    }

    public static ChatMessageFactory getInstance() {
        if (instance == null) {
            instance = new ChatMessageFactory();
        }
        return instance;
    }

    public ChatMessage create(String userId,
                              String author,
                              String type,
                              String body) {

        return new ChatMessage(
                rep.getId() + 1,
                userId,
                author,
                type,
                "",
                body,
                OffsetDateTime.now(),
                null
        );
    }

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
