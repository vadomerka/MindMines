package com.example.mindmines.services.observers;

import com.example.mindmines.models.game.equipment.types.Equipment;

import java.util.List;

public interface EquipObserver extends RepositoryObserver<Equipment> {
    void update(List<Equipment> upd);
}
