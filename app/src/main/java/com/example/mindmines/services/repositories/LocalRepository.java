package com.example.mindmines.services.repositories;

import android.content.Context;

import com.example.mindmines.db.entities.HabitEntity;
import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.models.interfaces.Identified;
import com.example.mindmines.models.interfaces.RepositoryItem;
import com.example.mindmines.services.auth.AuthManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class LocalRepository<TId extends Comparable<TId>, T extends RepositoryItem<TId>> implements Identified<TId> {
    protected List<T> array;
    protected Context context;

    public void init(Context context) {
        this.context = context;
        initArray();
    }

    public void initArray() {
        array = new ArrayList<>();
    }

    protected abstract TId defaultId();

    public TId getId() {
        return getAll().stream().map(T::getId).max(Comparable::compareTo).orElse(defaultId());
    }

    public List<T> getAll() {
        return array;
    }

    public List<T> getByUser() {
        String userId = new AuthManager(context).getUserId();
        return array.stream().filter(it -> userId != null && userId.equals(it.getUserId())).collect(Collectors.toList());
    }

    public void setAll(String userId, List<T> arr) {
        array = array.stream()
                .filter(it -> userId == null || !it.getUserId().equals(userId))
                .collect(Collectors.toList());
        array.addAll(arr);
    }

    public void add(T item) {
        array.add(item);
    }

    public void remove(T item) {
        array.remove(item);
    }

    private T get(TId id) {
        Optional<T> res = array.stream().filter(item -> item.getId().equals(id)).findFirst();
        return res.orElse(null);
    }

    public void update(T item) {
        T found = get(item.getId());
        int ind = array.indexOf(found);
        array.set(ind, item);
    }
}
