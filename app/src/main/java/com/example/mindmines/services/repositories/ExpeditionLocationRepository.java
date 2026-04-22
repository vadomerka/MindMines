package com.example.mindmines.services.repositories;

import com.example.mindmines.R;
import com.example.mindmines.models.game.ExpeditionLocation;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class ExpeditionLocationRepository extends LocalRepository<ExpeditionLocation> {
    @Override
    public void initArray() {
        array = new ArrayList<>();
        array.add(new ExpeditionLocation("exp1", "Дефолтная локация", String.valueOf(R.drawable.expedition_1)));
        array.add(new ExpeditionLocation("exp2", "Вторая локация", String.valueOf(R.drawable.expedition_2)));
        array.add(new ExpeditionLocation("exp3", "Неоткрытая локация", String.valueOf(R.drawable.expedition_3)));
    }

    @Override
    public ExpeditionLocation get(Object obj) {
        String itemId = (String) obj;
        Optional<ExpeditionLocation> res = array.stream().filter(i -> Objects.equals(i.getId(), itemId)).findFirst();
        return res.orElse(null);
    }

    @Override
    public void update(ExpeditionLocation item) {
        ExpeditionLocation found = get(item.getId());
        array.set(array.indexOf(found), item);
    }
}
