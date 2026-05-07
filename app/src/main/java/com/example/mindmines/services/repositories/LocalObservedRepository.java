package com.example.mindmines.services.repositories;

import android.content.Context;

import com.example.mindmines.models.interfaces.Observed;
import com.example.mindmines.models.interfaces.RepositoryItem;
import com.example.mindmines.services.observers.RepositoryObserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class LocalObservedRepository<TId extends Comparable<TId>,
                                              T extends RepositoryItem<TId>,
                                              TObserver extends RepositoryObserver<T>>
    extends LocalRepository<TId, T>
        implements Observed<TObserver> {
    protected List<TObserver> observers;

    @Override
    public void init(Context context) {
        super.init(context);
        observers = new ArrayList<>();
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

    @Override
    public void setAll(String userId, List<T> arr) {
        super.setAll(userId, arr);
        updateObservers();
    }

    @Override
    public void add(T item) {
        super.add(item);
        updateObservers(item);
    }

    @Override
    public void remove(T item) {
        super.remove(item);
        updateObservers();
    }

    @Override
    public void update(T item) {
        super.update(item);
        updateObservers(item);
    }
}
