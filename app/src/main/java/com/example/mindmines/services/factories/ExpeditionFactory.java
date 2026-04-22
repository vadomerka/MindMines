package com.example.mindmines.services.factories;

import com.example.mindmines.R;
import com.example.mindmines.db.entities.ExpeditionEntity;
import com.example.mindmines.models.game.Expedition;
import com.example.mindmines.services.repositories.ExpeditionRepository;
import com.example.mindmines.services.repositories.RepositoryService;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.OptionalInt;
import java.util.Random;

public class ExpeditionFactory {
    private static final Random rnd = new Random();
    private static final int variation = 5;
    private static final int baseValue = 10;

    private static int getId() {
        ExpeditionRepository rep = RepositoryService.getExpeditionRepository();
        OptionalInt rm = rep.getAll() != null ? rep.getAll().stream()
                .mapToInt(Expedition::getExpeditionId).max() : OptionalInt.of(0);
        return (rm.isPresent() ? rm.getAsInt() : 0) + 1;
    }

    public static Expedition generate() {
        return create(0, "Expedition 33", String.valueOf(R.drawable.h1), Duration.ofMinutes(5));
    }

    public static Expedition create(int level, String name, String image, Duration duration) {
        return new Expedition(
                getId(),
                name,
                image,
                level,
                OffsetDateTime.now(),
                OffsetDateTime.now().plus(duration),
                false
        );
    }

    public static Expedition createFromEntity(ExpeditionEntity entity) {
        return new Expedition(
                entity.expeditionId,
                entity.title,
                entity.type,
                entity.level,
                entity.start,
                entity.finish,
                entity.isFinished
        );
    }

    public static ExpeditionEntity createEntity(Expedition ex) {
        return new ExpeditionEntity(
                ex.getExpeditionId(),
                ex.getTitle(),
                ex.getType(),
                ex.getLevel(),
                ex.getStart(),
                ex.getFinish(),
                ex.isFinished()
        );
    }
}
