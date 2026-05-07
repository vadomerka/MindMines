package com.example.mindmines.services.converters;

import com.example.mindmines.db.entities.CharEntity;
import com.example.mindmines.models.game.characters.Char;
import com.google.gson.Gson;

public class CharConverter implements RepConverter<Integer, Char, CharEntity> {
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
