package com.example.mindmines.db.datasync;

import android.content.Context;

import com.example.mindmines.db.MindMinesDatabase;
import com.example.mindmines.db.dao.CharDao;
import com.example.mindmines.db.entities.CharEntity;
import com.example.mindmines.models.game.characters.Char;
import com.example.mindmines.services.factories.CharFactory;
import com.example.mindmines.services.repositories.RepositoryService;

import java.util.ArrayList;
import java.util.List;

public class CharDataSynchronizer implements DataSynchronizer {
    private final CharDao dao;

    public CharDataSynchronizer(Context context) {
        MindMinesDatabase db = MindMinesDatabase.getInstance(context);
        this.dao = db.charDao();
    }

    public void loadFromDB() {
        List<CharEntity> entities = dao.getAll();
        List<Char> chars = new ArrayList<>();

        for (CharEntity e : entities) {
            chars.add(CharFactory.getInstance().toItem(e));
        }

//        RepositoryService.getCharRepository().setAll(chars);
    }

    public void saveToDB() {
        List<Char> chars = RepositoryService.getCharRepository().getAll();
        List<CharEntity> entities = new ArrayList<>();

        for (Char c : chars) {
            entities.add(CharFactory.getInstance().toEntity(c));
        }

//        dao.deleteAll();
//        dao.insertAll(entities);
    }
}
