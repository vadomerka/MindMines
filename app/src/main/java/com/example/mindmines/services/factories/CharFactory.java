package com.example.mindmines.services.factories;

import com.example.mindmines.R;
import com.example.mindmines.db.entities.CharEntity;
import com.example.mindmines.models.game.characters.Char;
import com.example.mindmines.models.game.characters.CharStats;
import com.example.mindmines.models.game.characters.CharStatus;
import com.example.mindmines.models.game.equipment.CharEquipment;
import com.example.mindmines.models.game.equipment.types.Equipment;
import com.example.mindmines.services.repositories.implementations.CharRepository;
import com.example.mindmines.services.repositories.RepositoryService;
import com.google.gson.Gson;

import java.util.OptionalInt;
import java.util.Random;

public class CharFactory implements RepFactory<Integer, Char, CharEntity> {
    private final Random rnd = new Random();
    private static final int variation = 5;
    private static final int baseValue = 10;
    private final CharRepository rep;
    private static CharFactory instance;

    public CharFactory() {
        this.rep = RepositoryService.getCharRepository();
    }

    public static CharFactory getInstance() {
        if (instance == null) {
            instance = new CharFactory();
        }
        return instance;
    }

    private int getId() {
        OptionalInt rm = rep.getAll() != null
                ? rep.getAll().stream().mapToInt(Char::getId).max()
                : OptionalInt.of(0);
        return (rm.isPresent() ? rm.getAsInt() : 0) + 1;
    }

    public Char generate(String userId) {
        return generate(userId, 0, String.valueOf(R.drawable.h1));
    }

    public Char generate(String userId, int level, String image) {
        int atk = rnd.nextInt(variation) + baseValue * level;
        int defence = rnd.nextInt(variation) + baseValue * level;
        int speed = rnd.nextInt(variation) + baseValue * level;
        int hp = rnd.nextInt(variation) + baseValue * level;
        long maxExperience = rnd.nextInt(2) + (long) baseValue * level;
        return new Char(getId(), userId, "Char " + getId(),
                new CharStats(atk, defence, speed),
                new CharStatus(hp, level, maxExperience), new CharEquipment(), image);
    }

    public Char generate(String userId, int level, String image, Equipment[] equipment) {
        Char ch = generate(userId, level, image);
        for (Equipment eq: equipment) {
            ch.equip(eq);
        }
        return ch;
    }

    public Char toItem(CharEntity entity) {
        Gson g = new Gson();
        Char ch = g.fromJson(entity.charJson, Char.class);
        ch.setCharId(entity.charId);
        return ch;
    }

    public CharEntity toEntity(Char ch) {
        Gson g = new Gson();
        return new CharEntity(ch.getId(), ch.getUserId(), g.toJson(ch));
    }
}
