package com.example.mindmines.services.repositories;

import android.util.Log;

import com.example.mindmines.views.observers.RepositoryObserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class LocalObservedRepository<TId, T extends RepositoryItem<TId>, TObserver extends RepositoryObserver<T>>
    extends LocalRepository<TId, T>
        implements Observed<TObserver> {
    protected List<TObserver> observers;

    @Override
    public void init() {
        observers = new ArrayList<>();
        initArray();
        updateObservers();
    }

    public void subscribe(TObserver o) {
        observers.add(o);
    }

    public void unsubscribe(TObserver o) {
        observers.remove(o);
    }

    public void updateObservers() {
        for (TObserver o: observers) {
            o.update(new ArrayList<>());
        }
    }

    public void updateObservers(T upd) {
        for (TObserver o: observers) {
            o.update(Collections.singletonList(upd));
        }
    }

    public List<T> getAll() {
        return array;
    }

    @Override
    public void setAll(List<T> arr) {
        array = arr;
        updateObservers();
    }

    public void add(T item) {
        array.add(item);
        updateObservers(item);
    }

    public void remove(T item) {
        array.remove(item);
        updateObservers();
    }

    @Override
    public void update(T item) {
        T found = get(item.getId());
        array.set(array.indexOf(found), item);
        updateObservers(item);
    }
}
