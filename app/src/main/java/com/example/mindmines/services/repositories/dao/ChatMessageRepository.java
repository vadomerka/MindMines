package com.example.mindmines.services.repositories.dao;

import com.example.mindmines.db.MindMinesDatabase;
import com.example.mindmines.db.entities.ChatMessageEntity;
import com.example.mindmines.models.chat.ChatMessage;
import com.example.mindmines.services.converters.entities.ChatMessageConverter;
import com.example.mindmines.services.observers.ChatMessageObserver;
import com.example.mindmines.services.repositories.LocalDaoRepository;

import java.util.ArrayList;

public class ChatMessageRepository extends LocalDaoRepository<Integer, ChatMessage, ChatMessageEntity, ChatMessageObserver> {
    @Override
    public void initDao() {
        MindMinesDatabase db = MindMinesDatabase.getInstance(context);
        this.dao = db.messageDao();
    }

    @Override
    public void initConverter() {
        converter = new ChatMessageConverter();
    }

    protected Integer defaultId() {return 0;}

    @Override
    public void initArray() {
        array = new ArrayList<>();
    }
}
