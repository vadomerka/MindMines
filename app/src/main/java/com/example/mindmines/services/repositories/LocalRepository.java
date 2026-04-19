package com.example.mindmines.services.repositories;

import com.example.mindmines.views.observers.RepositoryObserver;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class LocalRepository<T, TObserver extends RepositoryObserver<T>> {
    protected List<T> array;
    protected List<TObserver> observers;

    public void init() {
        observers = new ArrayList<>();
        initArray();
    }

    public void initArray() {}

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

    public void setAll(List<T> arr) {
        array = arr;
        updateObservers();
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
        updateObservers();
    }
}
