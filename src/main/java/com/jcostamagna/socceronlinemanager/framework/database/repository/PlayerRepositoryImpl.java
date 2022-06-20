package com.jcostamagna.socceronlinemanager.framework.database.repository;

import com.jcostamagna.socceronlinemanager.domain.Player;
import com.jcostamagna.socceronlinemanager.domain.Team;
import com.jcostamagna.socceronlinemanager.framework.database.domain.PlayerEntity;
import com.jcostamagna.socceronlinemanager.framework.database.domain.TeamEntity;
import com.jcostamagna.socceronlinemanager.framework.database.repository.JPARepositories.PlayerJPARepository;
import com.jcostamagna.socceronlinemanager.usecases.ports.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PlayerRepositoryImpl implements PlayerRepository {

    @Autowired
    PlayerJPARepository playerJPARepository;

    @Override
    public Optional<Player> findById(Long player) {
        return this.mapToPlayer(playerJPARepository.findById(player));
    }

    @Override
    public Optional<PlayerEntity> findPlayerById(Long player) {
        return playerJPARepository.findById(player);
    }

    @Override
    public boolean existById(Long player) {
        return playerJPARepository.existsById(player);
    }

    @Override
    public PlayerEntity save(PlayerEntity player) {
        return playerJPARepository.save(player);
    }

    private Optional<Player> mapToPlayer(Optional<PlayerEntity> player) {
        return player.map(p -> Player.builder()
                .id(p.getId())
                .firstName(p.getFirstName())
                .lastName(p.getLastName())
                .position(p.getPosition())
                .value(p.getValue())
                .age(p.getAge())
                .country(p.getCountry())
                .team(this.mapToTeam(p.getTeam()))
                .build());
    }

    private Team mapToTeam(TeamEntity team) {
        return Team.builder()
                .id(team.getId())
                .name(team.getName())
                .country(team.getCountry())
                .budget(team.getBudget())
                .value(team.getValue())
                .build();
    }
}
