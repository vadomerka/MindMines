package com.example.mindmines.services.repositories;

public interface RepositoryItem <TId> {
    TId getId();

    String getUserId();
}
