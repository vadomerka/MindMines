package com.example.mindmines.services.repositories;

import com.example.mindmines.R;
import com.example.mindmines.models.game.characters.Char;
import com.example.mindmines.models.game.equipment.types.BodyArmor;
import com.example.mindmines.models.game.equipment.types.Equipment;
import com.example.mindmines.models.game.equipment.types.LegArmor;
import com.example.mindmines.models.game.equipment.types.Shield;
import com.example.mindmines.models.game.equipment.types.Sword;
import com.example.mindmines.services.factories.CharFactory;
import com.example.mindmines.views.observers.CharObserver;

import java.util.ArrayList;
import java.util.Optional;

public class CharRepository extends LocalObservedRepository<Char, CharObserver> {
    protected final int MAX_CHARACTERS = 4;

    @Override
    public void initArray() {
        array = new ArrayList<>();
        array.add(CharFactory.generate(1, String.valueOf(R.drawable.h1),
                new Equipment[] { new Sword(), new Shield(), new BodyArmor(), new LegArmor() }));
        array.add(CharFactory.generate(2, String.valueOf(R.drawable.h2),
                new Equipment[] { new Sword(), new LegArmor() }));
        array.add(CharFactory.generate(3, String.valueOf(R.drawable.h3), new Equipment[] { }));
    }

    @Override
    public void add(Char item) {
        if (array.size() == MAX_CHARACTERS) return;
        array.add(item);
    }

    @Override
    public Char get(Object obj) {
        int itemId = (int) obj;
        Optional<Char> res = array.stream().filter(i -> i.getCharId() == itemId).findFirst();
        return res.orElse(null);
    }

    @Override
    public void update(Char item) {
        Char found = get(item.getCharId());
        array.set(array.indexOf(found), item);
        updateObservers();
    }
}
