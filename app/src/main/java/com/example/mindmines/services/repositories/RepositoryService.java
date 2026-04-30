package com.example.mindmines.services.repositories;

import android.content.Context;

public class RepositoryService {
    private static CharRepository chRep = null;
    private static HabitRepository hRep = null;
    private static ExpeditionRepository eRep = null;
    private static ExpeditionLocationRepository elRep = null;
    private static ExpeditionCharRepository cheRep = null;
    private static ChatMessageRepository mRep = null;

    public static void initAll(Context context) {
        getCharRepository().init(context);
        getHabitRepository().init(context);
        getExpeditionRepository().init(context);
        getExpeditionLocationRepository().init(context);
        getExpeditionCharRepository().init(context);
        getChatMessageRepository().init(context);
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

    public static ChatMessageRepository getChatMessageRepository() {
        if (mRep == null) mRep = new ChatMessageRepository();
        return mRep;
    }
}
