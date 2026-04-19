package com.example.mindmines.views.observers;

import java.util.List;

public interface RepositoryObserver<T> {
    void update(List<T> upd);
}
