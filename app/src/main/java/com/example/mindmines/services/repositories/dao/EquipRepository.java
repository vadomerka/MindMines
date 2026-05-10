package com.example.mindmines.services.repositories.dao;

import com.example.mindmines.db.MindMinesDatabase;
import com.example.mindmines.db.entities.EquipEntity;
import com.example.mindmines.models.game.equipment.types.Equipment;
import com.example.mindmines.services.converters.entities.EquipConverter;
import com.example.mindmines.services.observers.EquipObserver;
import com.example.mindmines.services.repositories.LocalDaoRepository;

import java.util.ArrayList;

public class EquipRepository extends LocalDaoRepository<Integer, Equipment, EquipEntity, EquipObserver> {
    @Override
    public void initDao() {
        MindMinesDatabase db = MindMinesDatabase.getInstance(context);
        this.dao = db.equipDao();
    }

    public void initConverter() {
        converter = new EquipConverter();
    }

    @Override
    public void initArray() {
        array = new ArrayList<>();
    }

    protected Integer defaultId() {
        return 0;
    }
}
