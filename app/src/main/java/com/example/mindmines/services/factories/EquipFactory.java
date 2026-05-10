package com.example.mindmines.services.factories;

import com.example.mindmines.models.game.equipment.types.Equipment;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.services.repositories.dao.EquipRepository;

import java.util.List;

public class EquipFactory {
    private EquipRepository rep;
    private static EquipFactory instance;

    public EquipFactory() {
        this.rep = RepositoryService.getEquipRepository();
    }

    public static EquipFactory getInstance() {
        if (instance == null) {
            instance = new EquipFactory();
        }
        return instance;
    }
}
