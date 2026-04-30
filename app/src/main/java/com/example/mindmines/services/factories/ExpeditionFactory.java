package com.example.mindmines.services.factories;

import com.example.mindmines.R;
import com.example.mindmines.db.entities.ExpeditionEntity;
import com.example.mindmines.models.game.expeditions.Expedition;
import com.example.mindmines.services.repositories.ChatMessageRepository;
import com.example.mindmines.services.repositories.ExpeditionRepository;
import com.example.mindmines.services.repositories.RepositoryService;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.OptionalInt;
import java.util.Random;

public class ExpeditionFactory {
    private final Random rnd = new Random();
    private final int variation = 5;
    private final int baseValue = 10;

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

    private int getId() {
        OptionalInt rm = rep.getAll() != null ? rep.getAll().stream()
                .mapToInt(Expedition::getId).max() : OptionalInt.of(0);
        return (rm.isPresent() ? rm.getAsInt() : 0) + 1;
    }

    public Expedition generate(String userId) {
        return create(userId, 0, "Expedition 33", String.valueOf(R.drawable.expedition_1), Duration.ofMinutes(5));
    }

    public Expedition create(String userId, int level, String name, String image, Duration duration) {
        return new Expedition(
                getId(),
                userId,
                name,
                image,
                level,
                OffsetDateTime.now(),
                OffsetDateTime.now().plus(duration),
                false
        );
    }

    public Expedition createFromEntity(ExpeditionEntity entity) {
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

    public ExpeditionEntity createEntity(Expedition ex) {
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
