package com.example.mindmines.models.dto;

public class UserDTO {
    public Integer userID;
    public String name;
    public Integer level;

    public UserDTO(Integer userID, String name, Integer level) {
        this.userID = userID;
        this.name = name;
        this.level = level;
    }
}
