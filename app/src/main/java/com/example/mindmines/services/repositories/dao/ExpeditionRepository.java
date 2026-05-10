package com.example.mindmines.services.repositories.dao;

import com.example.mindmines.db.MindMinesDatabase;
import com.example.mindmines.db.entities.ExpeditionEntity;
import com.example.mindmines.models.game.expeditions.Expedition;
import com.example.mindmines.services.converters.entities.ExpeditionConverter;
import com.example.mindmines.services.observers.ExpeditionObserver;
import com.example.mindmines.services.repositories.LocalDaoRepository;

import java.util.ArrayList;

public class ExpeditionRepository extends LocalDaoRepository<Integer, Expedition, ExpeditionEntity, ExpeditionObserver> {
    @Override
    public void initDao() {
        MindMinesDatabase db = MindMinesDatabase.getInstance(context);
        this.dao = db.expeditionDao();
    }

    @Override
    public void initConverter() {
        converter = new ExpeditionConverter();
    }

    protected Integer defaultId() {
        return 0;
    }

    @Override
    public void initArray() {
        array = new ArrayList<>();
    }
}
