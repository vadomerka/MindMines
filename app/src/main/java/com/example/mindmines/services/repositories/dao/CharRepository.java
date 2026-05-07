package com.example.mindmines.services.repositories.dao;

import com.example.mindmines.R;
import com.example.mindmines.db.MindMinesDatabase;
import com.example.mindmines.db.entities.CharEntity;
import com.example.mindmines.models.game.characters.Char;
import com.example.mindmines.models.game.equipment.types.BodyArmor;
import com.example.mindmines.models.game.equipment.types.Equipment;
import com.example.mindmines.models.game.equipment.types.LegArmor;
import com.example.mindmines.models.game.equipment.types.Shield;
import com.example.mindmines.models.game.equipment.types.Sword;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.services.converters.CharConverter;
import com.example.mindmines.services.factories.CharFactory;
import com.example.mindmines.services.observers.CharObserver;
import com.example.mindmines.services.repositories.LocalDaoRepository;

import java.util.ArrayList;

public class CharRepository extends LocalDaoRepository<Integer, Char, CharEntity, CharObserver> {
    protected final int MAX_CHARACTERS = 4;

    @Override
    public void initDao() {
        MindMinesDatabase db = MindMinesDatabase.getInstance(context);
        this.dao = db.charDao();
    }

    public void initConverter() {
        converter = new CharConverter();
    }

    @Override
    public void initArray() {
        array = new ArrayList<>();
        String userId = new AuthManager(context).getUserId();
        CharFactory factory = CharFactory.getInstance();
        array.add(factory.generate(userId, 1, String.valueOf(R.drawable.g2),
                new Equipment[] { new Sword(), new Shield(), new BodyArmor(), new LegArmor() }));
        array.add(factory.generate(userId, 2, String.valueOf(R.drawable.g3),
                new Equipment[] { new Sword(), new LegArmor() }));
        array.add(factory.generate(userId, 3, String.valueOf(R.drawable.g4),
                new Equipment[] { }));
        array.add(factory.generate(userId, 5, String.valueOf(R.drawable.g5),
                new Equipment[] { }));
    }

    protected Integer defaultId() {return 0;}

    @Override
    public void add(Char item) {
        if (getByUser().size() >= MAX_CHARACTERS) return;
        super.add(item);
    }
}
