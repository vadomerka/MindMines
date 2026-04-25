package com.example.mindmines.services.repositories;

import com.example.mindmines.models.game.expeditions.Expedition;
import com.example.mindmines.views.observers.ExpeditionObserver;

import java.util.ArrayList;

public class ExpeditionRepository extends LocalObservedRepository<Integer, Expedition, ExpeditionObserver> {
    @Override
    public void initArray() {
        array = new ArrayList<>();
    }
}
