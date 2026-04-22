package com.example.mindmines.services.repositories;

import com.example.mindmines.views.observers.RepositoryObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class LocalObservedRepository<T, TObserver extends RepositoryObserver<T>>
    extends LocalRepository<T>
        implements Observed<TObserver> {
    protected List<TObserver> observers;

    @Override
    public void init() {
        observers = new ArrayList<>();
        initArray();
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

    public List<T> getAll() {
        return array;
    }

    @Override
    public void setAll(List<T> arr) {
        array = arr;
        updateObservers();
    }

    @Override
    public void update(T item) {
        T found = get(item);
        array.set(array.indexOf(found), item);
        updateObservers();
    }
}
