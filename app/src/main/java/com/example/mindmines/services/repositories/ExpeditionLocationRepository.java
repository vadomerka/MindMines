package com.example.mindmines.services.repositories;

import com.example.mindmines.R;
import com.example.mindmines.models.game.expeditions.ExpeditionLocation;

import java.util.ArrayList;

public class ExpeditionLocationRepository extends LocalRepository<String, ExpeditionLocation> {
    @Override
    public void initArray() {
        array = new ArrayList<>();
        array.add(new ExpeditionLocation("exp1", "Дефолтная локация", String.valueOf(R.drawable.expedition_2)));
        array.add(new ExpeditionLocation("exp2", "Вторая локация", String.valueOf(R.drawable.expedition_1)));
        array.add(new ExpeditionLocation("exp3", "Неоткрытая локация", String.valueOf(R.drawable.expedition_3)));
    }
}
