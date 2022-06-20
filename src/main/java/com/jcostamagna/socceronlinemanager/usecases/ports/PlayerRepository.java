package com.jcostamagna.socceronlinemanager.usecases.ports;

import com.jcostamagna.socceronlinemanager.domain.Player;
import com.jcostamagna.socceronlinemanager.framework.database.domain.PlayerEntity;

import java.util.Optional;

public interface PlayerRepository {
    Optional<Player> findById(Long player);

    Optional<PlayerEntity> findPlayerById(Long player);

    boolean existById(Long player);

    PlayerEntity save(PlayerEntity player);
}
