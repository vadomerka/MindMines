package com.example.mindmines.services.repositories;

public class RepositoryService {
    private static CharRepository chRep = null;
    private static HabitRepository hRep = null;
    private static ExpeditionRepository eRep = null;
    private static ExpeditionLocationRepository elRep = null;
    private static ExpeditionCharRepository cheRep = null;

    public static void initAll() {
        getCharRepository().init();
        getHabitRepository().init();
        getExpeditionRepository().init();
        getExpeditionLocationRepository().init();
        getExpeditionCharRepository().init();
    }

    public static CharRepository getCharRepository() {
        if (chRep == null) chRep = new CharRepository();
        return chRep;
    }

    public static HabitRepository getHabitRepository() {
        if (hRep == null) hRep = new HabitRepository();
        return hRep;
    }

    public static ExpeditionRepository getExpeditionRepository() {
        if (eRep == null) eRep = new ExpeditionRepository();
        return eRep;
    }

    public static ExpeditionLocationRepository getExpeditionLocationRepository() {
        if (elRep == null) elRep = new ExpeditionLocationRepository();
        return elRep;
    }

    public static ExpeditionCharRepository getExpeditionCharRepository() {
        if (cheRep == null) cheRep = new ExpeditionCharRepository();
        return cheRep;
    }
}
