package com.jcostamagna.socceronlinemanager.framework.database.repository;

import com.jcostamagna.socceronlinemanager.framework.database.domain.TeamEntity;
import com.jcostamagna.socceronlinemanager.framework.database.repository.JPARepositories.TeamJPARepository;
import com.jcostamagna.socceronlinemanager.usecases.ports.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TeamRepositoryImpl implements TeamRepository {

    @Autowired
    TeamJPARepository teamJPARepository;

    @Override
    public TeamEntity save(TeamEntity team) {
        return teamJPARepository.save(team);
    }

    @Override
    public Optional<TeamEntity> findById(long id) {
        return teamJPARepository.findById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return teamJPARepository.existsByName(name);
    }
}
