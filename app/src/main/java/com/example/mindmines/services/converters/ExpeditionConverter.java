package com.example.mindmines.services.converters;

import com.example.mindmines.R;
import com.example.mindmines.db.entities.ExpeditionEntity;
import com.example.mindmines.models.game.expeditions.Expedition;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.services.repositories.dao.ExpeditionRepository;

import java.time.Duration;
import java.time.OffsetDateTime;

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
