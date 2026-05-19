package com.example.mindmines.services.repositories.dao;

import com.example.mindmines.data.MindMinesDatabase;
import com.example.mindmines.data.entities.CharEntity;
import com.example.mindmines.models.game.characters.Char;
import com.example.mindmines.services.converters.entities.CharConverter;
import com.example.mindmines.services.observers.CharObserver;
import com.example.mindmines.services.repositories.LocalDaoRepository;

import java.util.ArrayList;

public class CharRepository extends LocalDaoRepository<Integer, Char, CharEntity, CharObserver> {
    protected final int MAX_CHARACTERS = 4;

    @Override
    public void initDao() {
        MindMinesDatabase db = MindMinesDatabase.getInstance(context);
        this.dao = db.charDao();
    }

    public void initConverter() {
        converter = new CharConverter();
    }

    @Override
    public void initArray() {
        array = new ArrayList<>();
    }

    protected Integer defaultId() {
        return 0;
    }

    @Override
    public void add(Char item) {
        if (getByUser().size() >= MAX_CHARACTERS) return;
        super.add(item);
    }
}
