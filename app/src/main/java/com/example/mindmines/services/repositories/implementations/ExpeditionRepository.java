package com.example.mindmines.services.repositories.implementations;

import com.example.mindmines.models.game.expeditions.Expedition;
import com.example.mindmines.services.observers.ExpeditionObserver;
import com.example.mindmines.services.repositories.LocalObservedRepository;

import java.util.ArrayList;

public class ExpeditionRepository extends LocalObservedRepository<Integer, Expedition, ExpeditionObserver> {
    @Override
    public void initArray() {
        array = new ArrayList<>();
    }
}
