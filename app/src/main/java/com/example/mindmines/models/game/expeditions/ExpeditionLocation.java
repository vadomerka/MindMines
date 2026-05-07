package com.example.mindmines.models.game.expeditions;

import com.example.mindmines.models.interfaces.RepositoryItem;

public class ExpeditionLocation implements RepositoryItem<String> {
    private final String id;
    private final String name;
    private final String image;

    public ExpeditionLocation(String id, String name, String imageResId) {
        this.id = id;
        this.name = name;
        this.image = imageResId;
    }

    public String getId() { return id; }
    public String getUserId() { return id; }  // Репозиторий статичен.
    public String getName() { return name; }
    public String getImage() { return image; }
}