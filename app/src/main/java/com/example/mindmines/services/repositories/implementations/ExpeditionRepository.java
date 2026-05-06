package com.example.mindmines.services.repositories.implementations;

import com.example.mindmines.db.entities.ExpeditionEntity;
import com.example.mindmines.models.game.expeditions.Expedition;
import com.example.mindmines.services.factories.ExpeditionFactory;
import com.example.mindmines.services.factories.HabitFactory;
import com.example.mindmines.services.observers.ExpeditionObserver;
import com.example.mindmines.services.repositories.LocalDaoRepository;
import com.example.mindmines.services.repositories.LocalObservedRepository;

import java.util.ArrayList;

public class ExpeditionRepository extends LocalDaoRepository<Integer, Expedition, ExpeditionEntity, ExpeditionObserver> {
    @Override
    public void initFactory() { factory = new ExpeditionFactory(); }
    @Override
    public void initArray() {
        array = new ArrayList<>();
    }
}
