package com.example.mindmines.services.repositories;

import com.example.mindmines.models.game.Char;
import com.example.mindmines.services.factories.CharFactory;
import com.example.mindmines.views.observers.CharObserver;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CharRepository {
    private static final String TAG = "Debug data sync";
    private static List<Char> array;
    private static List<CharObserver> observers;

    public static void init() {
        observers = new ArrayList<>();
        OffsetDateTime n = OffsetDateTime.now();
        array = new ArrayList<Char>() {
            {
                add(CharFactory.generate(1));
                add(CharFactory.generate(2));
                add(CharFactory.generate(3));
            }
        };
    }

    public static void subscribe(CharObserver o) {
        observers.add(o);
    }

    public static void unsubscribe(CharObserver o) {
        observers.remove(o);
    }

    public static List<Char> getAll() {
        return array;
    }

    public static void setAll(List<Char> arr) {
        array = arr;
        updateObservers();
    }

    public static void add(Char item) {
        array.add(item);
    }

    public static void remove(Char item) {
        array.remove(item);
    }

    public static Char get(int itemId) {
        Optional<Char> res = array.stream().filter(i -> i.getCharId() == itemId).findFirst();
        return res.orElse(null);
    }

    public static void update(Char item) {
        Char found = get(item.getCharId());
        array.set(array.indexOf(found), item);
        updateObservers();
    }

    public static void updateObservers() {
        for (CharObserver o: observers) {
            o.updateChars();
        }
    }
}
