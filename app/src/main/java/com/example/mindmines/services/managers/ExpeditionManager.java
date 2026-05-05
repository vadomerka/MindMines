package com.example.mindmines.services.managers;

import com.example.mindmines.models.game.expeditions.Expedition;
import com.example.mindmines.services.repositories.implementations.ExpeditionRepository;
import com.example.mindmines.services.repositories.RepositoryService;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ExpeditionManager {
    private static final ExpeditionRepository rep = RepositoryService.getExpeditionRepository();

    public static Expedition getLatestUnfinishedExpedition() {
        List<Expedition> expeditions = rep.getAll().stream()
                .filter(ex -> !ex.isFinished()).collect(Collectors.toList());
        if (expeditions.isEmpty()) return null;
        expeditions.sort(Comparator.comparing(Expedition::getStart));
        return expeditions.get(expeditions.size() - 1);
    }

    public static Expedition getLatestExpedition() {
        List<Expedition> expeditions = rep.getAll();
        if (expeditions.isEmpty()) return null;
        expeditions.sort(Comparator.comparing(Expedition::getStart));
        return expeditions.get(expeditions.size() - 1);
    }

    public static List<Expedition> getExpeditions() {
        return rep.getAll();
    }

    public static boolean isEnded(Expedition ex) {
        return OffsetDateTime.now().isAfter(ex.getFinish());
    }

    public static Expedition add(Expedition ex) {
        rep.add(ex);
        return ex;
    }

    public static void finishExp(Expedition ex) {
        ex.setFinished(true);
        rep.update(ex);
    }

    public static void removeLast(Expedition ex) {
        rep.remove(ex);
    }
}
