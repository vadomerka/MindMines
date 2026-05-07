package com.example.mindmines.services.converters;

import com.example.mindmines.db.entities.UserStatusEntity;
import com.example.mindmines.models.user.UserStatus;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.services.repositories.dao.UserStatusRepository;

public class UserStatusConverter implements RepConverter<String, UserStatus, UserStatusEntity> {
    public UserStatus toItem(UserStatusEntity entity) {
        return new UserStatus(
                entity.userId,
                entity.level,
                entity.experience,
                entity.maxExperience,
                entity.coins
        );
    }

    public UserStatusEntity toEntity(UserStatus ex) {
        return new UserStatusEntity(
                ex.getId(),
                ex.getLevel(),
                ex.getExperience(),
                ex.getMaxExperience(),
                ex.getCoins()
        );
    }
}
