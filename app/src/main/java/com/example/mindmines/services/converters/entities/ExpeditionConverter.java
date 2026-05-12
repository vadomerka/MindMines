package com.example.mindmines.services.converters.entities;

import com.example.mindmines.data.entities.ExpeditionEntity;
import com.example.mindmines.models.game.expeditions.Expedition;

public class ExpeditionConverter implements RepConverter<Integer, Expedition, ExpeditionEntity> {
    public Expedition toItem(ExpeditionEntity entity) {
        return new Expedition(
                entity.expeditionId,
                entity.userId,
                entity.title,
                entity.type,
                entity.level,
                entity.start,
                entity.finish,
                entity.isFinished
        );
    }

    public ExpeditionEntity toEntity(Expedition ex) {
        return new ExpeditionEntity(
                ex.getId(),
                ex.getUserId(),
                ex.getTitle(),
                ex.getType(),
                ex.getLevel(),
                ex.getStart(),
                ex.getFinish(),
                ex.isFinished()
        );
    }
}
