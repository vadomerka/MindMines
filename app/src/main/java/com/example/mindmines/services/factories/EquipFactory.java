package com.example.mindmines.services.factories;

import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.services.repositories.dao.EquipRepository;

public class EquipFactory {
    private static EquipFactory instance;
    private final EquipRepository rep;

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
