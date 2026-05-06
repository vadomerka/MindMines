package com.example.mindmines.db.datasync;

import android.content.Context;

import com.example.mindmines.db.MindMinesDatabase;
import com.example.mindmines.db.dao.ExpeditionCharCrossRefDao;
import com.example.mindmines.db.dao.ExpeditionDao;
import com.example.mindmines.db.entities.ExpeditionEntity;
import com.example.mindmines.db.entities.crossref.ExpeditionCharCrossRef;
import com.example.mindmines.models.game.expeditions.Expedition;
import com.example.mindmines.services.factories.ExpeditionFactory;
import com.example.mindmines.services.repositories.RepositoryService;

import java.util.ArrayList;
import java.util.List;

public class ExpeditionCharDataSynchronizer implements DataSynchronizer {
    private final ExpeditionCharCrossRefDao dao;

    public ExpeditionCharDataSynchronizer(Context context) {
        MindMinesDatabase db = MindMinesDatabase.getInstance(context);
        this.dao = db.expeditionCharCrossRefDao();
    }

    public void loadFromDB() {
        List<ExpeditionCharCrossRef> entities = dao.getAll();
//        RepositoryService.getExpeditionCharRepository().setAll(entities);
    }

    public void saveToDB() {
        List<ExpeditionCharCrossRef> entities = RepositoryService.getExpeditionCharRepository().getAll();

//        dao.deleteAll();
//        dao.insertAll(entities);
    }
}
