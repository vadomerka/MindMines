package com.example.mindmines.services.factories;

import com.example.mindmines.db.entities.UserStatusEntity;
import com.example.mindmines.models.user.UserStatus;
import com.example.mindmines.services.converters.entities.RepConverter;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.services.repositories.dao.UserStatusRepository;

public class UserStatusFactory {
    private static UserStatusFactory instance;

    public UserStatusFactory() {
        UserStatusRepository rep = RepositoryService.getUserStatusRepository();
    }

    public static UserStatusFactory getInstance() {
        if (instance == null) {
            instance = new UserStatusFactory();
        }
        return instance;
    }

    public UserStatus create(String userId) {
        return new UserStatus(
                userId,
                0,
                0L,
                10L,
                0L
        );
    }

    public UserStatus create(String userId,
                             Integer level,
                             Long experience,
                             Long maxExperience,
                             Long coins) {
        return new UserStatus(
                userId,
                level,
                experience,
                maxExperience,
                coins
        );
    }
}
