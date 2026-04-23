package com.example.mindmines.services.managers;

import com.example.mindmines.models.game.expeditions.Expedition;
import com.example.mindmines.services.repositories.ExpeditionRepository;
import com.example.mindmines.services.repositories.RepositoryService;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ExpeditionManager {
    private static ExpeditionRepository rep = RepositoryService.getExpeditionRepository();

    public static Expedition getLatestUnfinishedExpedition() {
        List<Expedition> expeditions = rep.getAll().stream()
                .filter(Expedition::isFinished).collect(Collectors.toList());
        if (expeditions.isEmpty()) return null;
        expeditions.sort(Comparator.comparing(Expedition::getStart));
        return expeditions.getLast();
    }

    public static boolean isEnded(Expedition ex) {
        return OffsetDateTime.now().isAfter(ex.getFinish());
    }

    public static Expedition add(Expedition ex) {
        rep.add(ex);
        return ex;
    }
}
