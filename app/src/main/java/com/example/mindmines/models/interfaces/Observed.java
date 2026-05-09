package com.example.mindmines.models.interfaces;

public interface Observed<TObserver> {
    void subscribe(TObserver o);

    void unsubscribe(TObserver o);

    void updateObservers();
}
