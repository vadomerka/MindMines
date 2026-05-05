package com.example.mindmines.services.repositories;

import android.content.Context;

import androidx.room.Dao;

import java.util.List;
import java.util.Optional;

public abstract class LocalDaoRepository<TId, T extends RepositoryItem<TId>> extends LocalRepository<TId, T> {
//    private Dao
    public void init(Context context) {
        initArray();
        this.context = context;
    }

    public void initArray() {}

    public List<T> getAll() {
        return array;
    }

    public void setAll(List<T> arr) {
        array = arr;
    }

    public void add(T item) {
        array.add(item);
    }

    public void remove(T item) {
        array.remove(item);
    }

    public T get(TId id) {
        Optional<T> res = array.stream().filter(item -> item.getId().equals(id)).findFirst();
        return res.orElse(null);
    }

    public void update(T item) {
        T found = get(item.getId());
        array.set(array.indexOf(found), item);
    }
}
