package com.example.mindmines.services.factories;

import com.example.mindmines.R;
import com.example.mindmines.db.entities.CharEntity;
import com.example.mindmines.models.game.characters.Char;
import com.example.mindmines.models.game.characters.CharStats;
import com.example.mindmines.models.game.characters.CharStatus;
import com.example.mindmines.models.game.equipment.CharEquipment;
import com.example.mindmines.models.game.equipment.types.Equipment;
import com.example.mindmines.services.repositories.CharRepository;
import com.google.gson.Gson;

import java.util.OptionalInt;
import java.util.Random;

public class CharFactory {
    private static final OptionalInt rm = CharRepository.getAll() != null ? CharRepository.getAll().stream().mapToInt(Char::getCharId).max() : OptionalInt.of(0);
    private static int localId = rm.isPresent() ? rm.getAsInt() : 0;
    private static final Random rnd = new Random();
    private static final int variation = 5;
    private static final int baseValue = 10;

    public static Char generate() {
        return generate(0, String.valueOf(R.drawable.h1));
    }

    public static Char generate(int level, String image) {
        int atk = rnd.nextInt(variation) + baseValue * level;
        int defence = rnd.nextInt(variation) + baseValue * level;
        int speed = rnd.nextInt(variation) + baseValue * level;
        return new Char(localId++, "Char " + localId,
                new CharStats(atk, defence, speed),
                new CharStatus(), new CharEquipment(), image);
    }

    public static Char generate(int level, String image, Equipment[] equipment) {
        Char ch = generate(level, image);
        for (Equipment eq: equipment) {
            ch.equip(eq);
        }
        return ch;
    }

    public static Char createFromEntity(CharEntity entity) {
        Gson g = new Gson();
        Char ch = g.fromJson(entity.charJson, Char.class);
        ch.setCharId(entity.charId);
        return ch;
    }

    public static CharEntity createEntity(Char ch) {
        Gson g = new Gson();
        return new CharEntity(ch.getCharId(), g.toJson(ch));
    }
}
