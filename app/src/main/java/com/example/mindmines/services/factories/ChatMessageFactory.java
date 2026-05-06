package com.example.mindmines.services.factories;

import com.example.mindmines.db.entities.ChatMessageEntity;
import com.example.mindmines.models.chat.ChatMessage;
import com.example.mindmines.services.repositories.implementations.ChatMessageRepository;
import com.example.mindmines.services.repositories.RepositoryService;

import java.time.OffsetDateTime;
import java.util.OptionalInt;

public class ChatMessageFactory implements RepFactory<Integer, ChatMessage, ChatMessageEntity>{
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

    private int getId() {
        OptionalInt rm = rep.getAll() != null
                ? rep.getAll().stream().mapToInt(ChatMessage::getId).max()
                : OptionalInt.of(0);
        return (rm.isPresent() ? rm.getAsInt() : 0) + 1;
    }

    public ChatMessage create(String userId,
                              String author,
                              String type,
                              String body) {

        return new ChatMessage(
                getId(),
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
