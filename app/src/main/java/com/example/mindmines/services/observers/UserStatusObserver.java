package com.example.mindmines.services.observers;

import com.example.mindmines.models.user.UserStatus;

import java.util.List;

public interface UserStatusObserver extends RepositoryObserver<UserStatus> {
    void update(List<UserStatus> upd);
}
