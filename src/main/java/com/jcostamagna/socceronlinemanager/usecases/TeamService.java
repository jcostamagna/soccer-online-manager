package com.jcostamagna.socceronlinemanager.usecases;

import com.jcostamagna.socceronlinemanager.adapters.controller.exceptions.BadRequestException;
import com.jcostamagna.socceronlinemanager.adapters.domain.request.TeamRequest;
import com.jcostamagna.socceronlinemanager.domain.validator.CountryValidator;
import com.jcostamagna.socceronlinemanager.framework.database.domain.TeamEntity;
import com.jcostamagna.socceronlinemanager.framework.database.domain.UserEntity;
import com.jcostamagna.socceronlinemanager.usecases.ports.AuthenticationManagerService;
import com.jcostamagna.socceronlinemanager.usecases.ports.TeamRepository;
import com.jcostamagna.socceronlinemanager.usecases.ports.UserRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Log
@Service
public class TeamService {
    @Autowired
    AuthenticationManagerService authenticationManagerService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    CountryValidator countryValidator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public UserEntity getTeamInformation() {
        return authenticationManagerService.getLoggedUser();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public TeamEntity update(long id, TeamRequest teamRequest) {
        this.logRequest(teamRequest);

        UserEntity userEntity = authenticationManagerService.getLoggedUser();

        TeamEntity teamEntity = userEntity.getTeam();

        applyValidations(id, teamRequest, teamEntity);

        teamEntity.setCountry(teamRequest.getCountry());
        teamEntity.setName(teamRequest.getName());

        return teamRepository.save(teamEntity);
    }

    private void applyValidations(long id, TeamRequest teamRequest, TeamEntity teamEntity) {
        if (teamEntity.getId() != id) {
            throw new BadRequestException("Team id is not from user");
        }

        if(!countryValidator.validate(teamRequest.getCountry())) {
            throw new BadRequestException("Team country is not valid");
        }

        if(teamRepository.existsByName(teamRequest.getName()) && !teamRequest.getName().equals(teamEntity.getName())) {
            throw new BadRequestException("Team name is already used");
        }
    }

    private void logRequest(TeamRequest teamRequest) {
        log.info(String.format("Team Update: %s", teamRequest));
    }
}
