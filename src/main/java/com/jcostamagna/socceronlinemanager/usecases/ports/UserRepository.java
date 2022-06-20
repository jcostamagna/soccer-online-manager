package com.jcostamagna.socceronlinemanager.usecases.ports;

import com.jcostamagna.socceronlinemanager.domain.User;
import com.jcostamagna.socceronlinemanager.framework.database.domain.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    boolean existsByEmail(String email);

    UserEntity save(User user);

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findAll();
}
