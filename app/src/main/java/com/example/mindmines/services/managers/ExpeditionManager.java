package com.example.mindmines.services.managers;

import android.content.Context;

import com.example.mindmines.models.game.expeditions.Expedition;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.services.repositories.dao.ExpeditionRepository;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ExpeditionManager {
    private final ExpeditionRepository rep;

    private final Context context;
    private static ExpeditionManager instance;

    private ExpeditionManager(Context context) {
        this.context = context;
        rep = RepositoryService.getExpeditionRepository();
    }

    public static ExpeditionManager getInstance(Context context) {
        if (instance == null) {
            instance = new ExpeditionManager(context);
        }
        return instance;
    }

    public Expedition getLatestUnfinishedExpedition() {
        List<Expedition> expeditions = rep.getByUser().stream()
                .filter(ex -> !ex.isFinished()).collect(Collectors.toList());
        if (expeditions.isEmpty()) return null;
        expeditions.sort(Comparator.comparing(Expedition::getStart));
        return expeditions.get(expeditions.size() - 1);
    }

    public Expedition getLatestExpedition() {
        List<Expedition> expeditions = rep.getByUser();
        if (expeditions.isEmpty()) return null;
        expeditions.sort(Comparator.comparing(Expedition::getStart));
        return expeditions.get(expeditions.size() - 1);
    }

    public List<Expedition> getExpeditions() {
        return rep.getByUser();
    }

    public boolean isEnded(Expedition ex) {
        return OffsetDateTime.now().isAfter(ex.getFinish());
    }

    public Expedition add(Expedition ex) {
        rep.add(ex);
        return ex;
    }

    public void finishExp(Expedition ex) {
        ex.setFinished(true);
        rep.update(ex);
        UserStatusManager.getInstance(context).gain(ex);
        CharManager.getInstance(context).gain(ex);
    }

    public void removeLast(Expedition ex) {
        rep.remove(ex);
    }
}
