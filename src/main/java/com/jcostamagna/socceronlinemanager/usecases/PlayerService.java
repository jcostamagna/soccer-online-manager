package com.jcostamagna.socceronlinemanager.usecases;

import com.jcostamagna.socceronlinemanager.adapters.controller.exceptions.BadRequestException;
import com.jcostamagna.socceronlinemanager.adapters.domain.request.PlayerRequest;
import com.jcostamagna.socceronlinemanager.domain.validator.CountryValidator;
import com.jcostamagna.socceronlinemanager.framework.database.domain.PlayerEntity;
import com.jcostamagna.socceronlinemanager.framework.database.domain.UserEntity;
import com.jcostamagna.socceronlinemanager.usecases.ports.AuthenticationManagerService;
import com.jcostamagna.socceronlinemanager.usecases.ports.PlayerRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Log
@Service
public class PlayerService {
    @Autowired
    AuthenticationManagerService authenticationManagerService;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    CountryValidator countryValidator;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public PlayerEntity update(long id, PlayerRequest playerRequest) {
        this.logRequest(playerRequest);

        UserEntity userEntity = authenticationManagerService.getLoggedUser();

        PlayerEntity playerEntity = this.findPlayerInUser(userEntity, id);

        applyValidation(playerRequest);

        playerEntity.setCountry(playerRequest.getCountry());
        playerEntity.setFirstName(playerRequest.getFirstName());
        playerEntity.setLastName(playerRequest.getLastName());

        return playerRepository.save(playerEntity);
    }

    private void applyValidation(PlayerRequest playerRequest) {
        if(!countryValidator.validate(playerRequest.getCountry())) {
            throw new BadRequestException("Player country is not valid");
        }
    }

    private PlayerEntity findPlayerInUser(UserEntity userEntity, long id) {
        return userEntity.getTeam()
                .getPlayers()
                .stream()
                .filter(player -> player.getId() == id)
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Player id is not from user"));
    }

    private void logRequest(PlayerRequest playerRequest) {
        log.info(String.format("Player update: %s", playerRequest));
    }
}
