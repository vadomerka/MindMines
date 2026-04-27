package com.example.mindmines.services.observers;

import java.util.List;

public interface RepositoryObserver<T> {
    void update(List<T> upd);
}
