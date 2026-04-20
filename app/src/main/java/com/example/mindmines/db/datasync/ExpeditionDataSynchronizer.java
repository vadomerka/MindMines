package com.example.mindmines.db.datasync;

import android.content.Context;

import com.example.mindmines.db.MindMinesDatabase;
import com.example.mindmines.db.dao.ExpeditionDao;
import com.example.mindmines.db.entities.ExpeditionEntity;
import com.example.mindmines.models.game.Expedition;
import com.example.mindmines.services.factories.ExpeditionFactory;
import com.example.mindmines.services.repositories.RepositoryService;

import java.util.ArrayList;
import java.util.List;

public class ExpeditionDataSynchronizer implements DataSynchronizer {
    private final ExpeditionDao dao;

    public ExpeditionDataSynchronizer(Context context) {
        MindMinesDatabase db = MindMinesDatabase.getInstance(context);
        this.dao = db.expeditionDao();
    }

    public void loadFromDB() {
        List<ExpeditionEntity> entities = dao.getAll();
        List<Expedition> expeditions = new ArrayList<>();

        for (ExpeditionEntity e : entities) {
            expeditions.add(ExpeditionFactory.createFromEntity(e));
        }

        RepositoryService.getExpeditionRepository().setAll(expeditions);
    }

    public void saveToDB() {
        List<Expedition> expeditions = RepositoryService.getExpeditionRepository().getAll();
        List<ExpeditionEntity> entities = new ArrayList<>();

        for (Expedition e : expeditions) {
            entities.add(ExpeditionFactory.createEntity(e));
        }

        dao.deleteAll();
        dao.insertAll(entities);
    }
}
