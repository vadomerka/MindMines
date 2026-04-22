package com.example.mindmines.services.repositories;

import com.example.mindmines.views.observers.RepositoryObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class LocalRepository<T> {
    protected List<T> array;

    public void init() {
        initArray();
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

    public T get(Object id) {
        Optional<T> res = array.stream().filter(item -> item.equals(id)).findFirst();
        return res.orElse(null);
    }

    public void update(T item) {
        T found = get(item);
        array.set(array.indexOf(found), item);
    }
}
