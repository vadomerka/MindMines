package com.example.mindmines.services.repositories;

import android.content.Context;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class LocalRepository<TId, T extends RepositoryItem<TId>> {
    protected List<T> array;
    protected Context context;

    public void init(Context context) {
        this.context = context;
        initArray();
    }

    public void initArray() {}

    public List<T> getAll() {
        return array;
    }

    public List<T> getByUser(String userId) {
        return array.stream().filter(it -> userId.equals(it.getUserId())).collect(Collectors.toList());
    }

    public void setAll(String userId, List<T> arr) {
        array = array.stream().filter(it -> !it.getUserId().equals(userId)).collect(Collectors.toList());
        array.addAll(arr);
    }

    public void add(T item) {
        array.add(item);
    }

    public void remove(T item) {
        array.remove(item);
    }

    public T get(TId id) {
        Optional<T> res = getAll().stream().filter(item -> item.getId().equals(id)).findFirst();
        return res.orElse(null);
    }

    public void update(T item) {
        T found = get(item.getId());
        array.set(array.indexOf(found), item);
    }
}
