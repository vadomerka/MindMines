package com.example.mindmines.services.factories;

import com.example.mindmines.models.game.Char;
import com.example.mindmines.models.game.CharStats;
import com.example.mindmines.models.game.CharStatus;
import com.example.mindmines.models.game.equipment.CharEquipment;
import com.example.mindmines.services.repositories.CharRepository;

import java.util.OptionalInt;
import java.util.Random;

public class CharFactory {
    private static final OptionalInt rm = CharRepository.getAll() != null ? CharRepository.getAll().stream().mapToInt(Char::getCharId).max() : OptionalInt.of(0);
    private static int localId = rm.isPresent() ? rm.getAsInt() : 0;
    private static final Random rnd = new Random();
    private static final int variation = 5;
    private static final int baseValue = 10;

    public static Char generate() {
        return generate(0);
    }

    public static Char generate(int level) {
        int atk = rnd.nextInt(variation) + baseValue * level;
        int defence = rnd.nextInt(variation) + baseValue * level;
        int speed = rnd.nextInt(variation) + baseValue * level;
        return new Char(localId++, "Char " + localId,
                new CharStats(atk, defence, speed),
                new CharStatus(), new CharEquipment());
    }
}
