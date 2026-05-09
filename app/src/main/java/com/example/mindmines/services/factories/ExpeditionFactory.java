package com.example.mindmines.services.factories;

import com.example.mindmines.R;
import com.example.mindmines.db.entities.ExpeditionEntity;
import com.example.mindmines.models.game.expeditions.Expedition;
import com.example.mindmines.services.converters.entities.RepConverter;
import com.example.mindmines.services.repositories.dao.ExpeditionRepository;
import com.example.mindmines.services.repositories.RepositoryService;

import java.time.Duration;
import java.time.OffsetDateTime;

public class ExpeditionFactory implements RepConverter<Integer, Expedition, ExpeditionEntity> {
    private final ExpeditionRepository rep;
    private static ExpeditionFactory instance;

    public ExpeditionFactory() {
        this.rep = RepositoryService.getExpeditionRepository();
    }

    public static ExpeditionFactory getInstance() {
        if (instance == null) {
            instance = new ExpeditionFactory();
        }
        return instance;
    }

    public Expedition generate(String userId) {
        return create(userId, 0, "Expedition 33", String.valueOf(R.drawable.expedition_1), Duration.ofMinutes(5));
    }

    public Expedition create(String userId, int level, String name, String image, Duration duration) {
        return new Expedition(
                rep.getId() + 1,
                userId,
                name,
                image,
                level,
                OffsetDateTime.now(),
                OffsetDateTime.now().plus(duration),
                false
        );
    }

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
