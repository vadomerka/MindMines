package com.example.mindmines.models.user;

public class User {
    public Integer userID;
    public String name;
    public Integer level;

    public User(Integer userID, String name, Integer level) {
        this.userID = userID;
        this.name = name;
        this.level = level;
    }
}
