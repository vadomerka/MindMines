package com.example.mindmines.services.repositories.implementations;

import com.example.mindmines.db.entities.ChatMessageEntity;
import com.example.mindmines.models.chat.ChatMessage;
import com.example.mindmines.services.factories.ChatMessageFactory;
import com.example.mindmines.services.observers.ChatMessageObserver;
import com.example.mindmines.services.repositories.LocalDaoRepository;

import java.util.ArrayList;

public class ChatMessageRepository extends LocalDaoRepository<Integer, ChatMessage, ChatMessageEntity, ChatMessageObserver> {
    @Override
    public void initFactory() {
        factory = new ChatMessageFactory();
    }

    @Override
    public void initArray() {
        array = new ArrayList<>();
    }
}
