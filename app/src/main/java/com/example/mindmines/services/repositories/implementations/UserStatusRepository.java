package com.example.mindmines.services.repositories.implementations;

import com.example.mindmines.models.user.UserStatus;
import com.example.mindmines.services.observers.UserStatusObserver;
import com.example.mindmines.services.repositories.LocalObservedRepository;

public class UserStatusRepository extends LocalObservedRepository<String, UserStatus, UserStatusObserver> {
}
