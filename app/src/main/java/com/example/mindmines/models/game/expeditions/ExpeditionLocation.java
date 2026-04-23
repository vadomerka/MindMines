package com.example.mindmines.models.game.expeditions;

public class ExpeditionLocation {
    private String id;
    private String name;
    private String image;

    public ExpeditionLocation(String id, String name, String imageResId) {
        this.id = id;
        this.name = name;
        this.image = imageResId;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getImage() { return image; }
}