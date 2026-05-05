package com.example.mindmines.services.repositories.implementations;

import com.example.mindmines.models.chat.ChatMessage;
import com.example.mindmines.services.observers.ChatMessageObserver;
import com.example.mindmines.services.repositories.LocalObservedRepository;

import java.util.ArrayList;

public class ChatMessageRepository extends LocalObservedRepository<Integer, ChatMessage, ChatMessageObserver> {
    @Override
    public void initArray() {
        array = new ArrayList<>();
    }
}
