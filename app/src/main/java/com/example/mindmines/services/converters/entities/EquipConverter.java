package com.example.mindmines.services.converters.entities;

import com.example.mindmines.db.entities.EquipEntity;
import com.example.mindmines.models.game.characters.CharStats;
import com.example.mindmines.models.game.equipment.types.Equipment;
import com.google.gson.Gson;

public class EquipConverter implements RepConverter<Integer, Equipment, EquipEntity> {
    public Equipment toItem(EquipEntity entity) {
        Gson g = new Gson();
        return new Equipment(
                entity.equipId,
                entity.userId,
                entity.image,
                entity.level,
                entity.price,
                g.fromJson(entity.equipStats, CharStats.class),
                entity.slotType
        );
    }

    public EquipEntity toEntity(Equipment ch) {
        Gson g = new Gson();
        return new EquipEntity(
                ch.getId(),
                ch.getUserId(),
                ch.getImage(),
                ch.getLevel(),
                ch.getPrice(),
                g.toJson(ch.getEquipStats()),
                ch.getSlotType()
        );
    }
}
