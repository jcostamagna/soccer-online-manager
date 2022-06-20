package com.jcostamagna.socceronlinemanager.usecases.ports;

import com.jcostamagna.socceronlinemanager.framework.database.domain.TeamEntity;

import java.util.Optional;

public interface TeamRepository {
    TeamEntity save(TeamEntity team);

    Optional<TeamEntity> findById(long id);

    boolean existsByName(String name);
}
