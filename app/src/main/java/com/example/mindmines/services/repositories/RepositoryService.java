package com.example.mindmines.services.repositories;

public class RepositoryService {
    private static CharRepository chRep = null;
    private static HabitRepository hRep = null;
    private static ExpeditionRepository eRep = null;

    public static void initAll() {
        getCharRepository().init();
        getHabitRepository().init();
        getExpeditionRepository().init();
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
}
