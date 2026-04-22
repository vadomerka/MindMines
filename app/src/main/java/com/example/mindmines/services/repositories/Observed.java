package com.example.mindmines.services.repositories;

public interface Observed<TObserver> {
    void subscribe(TObserver o);

    void unsubscribe(TObserver o);

    void updateObservers();
}
