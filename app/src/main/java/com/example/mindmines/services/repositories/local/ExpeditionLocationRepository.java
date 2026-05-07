package com.example.mindmines.services.repositories.local;

import com.example.mindmines.R;
import com.example.mindmines.models.game.expeditions.ExpeditionLocation;
import com.example.mindmines.services.repositories.LocalRepository;

import java.util.ArrayList;

public class ExpeditionLocationRepository extends LocalRepository<String, ExpeditionLocation> {
    @Override
    public void initArray() {
        array = new ArrayList<>();
        array.add(new ExpeditionLocation("exp1", "Пещера обмана", String.valueOf(R.drawable.expedition_1)));
        array.add(new ExpeditionLocation("exp2", "Замок предрассудков", String.valueOf(R.drawable.expedition_2)));
        array.add(new ExpeditionLocation("exp3", "Подземелье сознатики", String.valueOf(R.drawable.expedition_3)));
    }

    protected String defaultId() {return "id";}
}
