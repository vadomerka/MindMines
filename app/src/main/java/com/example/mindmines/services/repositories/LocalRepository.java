package com.example.mindmines.services.repositories;

import android.content.Context;

import java.util.List;
import java.util.Optional;

public abstract class LocalRepository<TId, T extends RepositoryItem<TId>> {
    protected List<T> array;
    protected Context context;

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
