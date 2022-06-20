package com.jcostamagna.socceronlinemanager.usecases;

import com.jcostamagna.socceronlinemanager.adapters.controller.exceptions.BadRequestException;
import com.jcostamagna.socceronlinemanager.adapters.domain.request.SignupRequest;
import com.jcostamagna.socceronlinemanager.domain.Player;
import com.jcostamagna.socceronlinemanager.domain.Team;
import com.jcostamagna.socceronlinemanager.domain.User;
import com.jcostamagna.socceronlinemanager.domain.enums.PlayerPosition;
import com.jcostamagna.socceronlinemanager.domain.faker.FakerGenerator;
import com.jcostamagna.socceronlinemanager.domain.validator.CountryValidator;
import com.jcostamagna.socceronlinemanager.framework.database.domain.UserEntity;
import com.jcostamagna.socceronlinemanager.usecases.ports.TeamRepository;
import com.jcostamagna.socceronlinemanager.usecases.ports.UserRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Log
@Service
public class TeamGenerator {

    protected static final int INITIAL_PLAYER_VALUE = 1000000;
    protected static final int INITAL_TEAM_VALUE = calculateQuantityOfPlayers() * INITIAL_PLAYER_VALUE;
    protected static final int INITIAL_TEAM_BUDGET = 5000000;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    FakerGenerator faker;

    @Autowired
    CountryValidator countryValidator;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public UserEntity generateTeam(SignupRequest signUpRequest) {
        this.logRequest(signUpRequest);

        this.validateIfUserExists(signUpRequest);

        List<Player> players = this.generatePlayers();

        Team team = generateTeam(players);

        User user = User.builder()
                .email(signUpRequest.getEmail())
                .password(signUpRequest.getPassword())
                .team(team)
                .build();

        return userRepository.save(user);
    }

    private void validateIfUserExists(SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email is already used");
        }
    }

    private Team generateTeam(List<Player> players) {
        return Team.builder()
                .country(this.createValidCountry())
                .name(this.createTeamName())
                .value(INITAL_TEAM_VALUE)
                .budget(INITIAL_TEAM_BUDGET)
                .players(players)
                .build();
    }

    private String createTeamName() {
        String name = faker.footballClubName();

        while (teamRepository.existsByName(name)) {
            name = faker.footballClubName();
        }

        return name;
    }

    private String createValidCountry() {
        String country = faker.generateCountry();

        while (!countryValidator.validate(country)) {
            country = faker.generateCountry();
        }

        return country;
    }

    protected List<Player> generatePlayers() {
        List<String> playersPositions = generatePlayerPositions();

        return playersPositions.stream().map( position ->
                Player.builder()
                    .firstName(faker.generateFirstName())
                    .lastName(faker.generateLastName())
                    .age(faker.generateAge())
                    .country(this.createValidCountry())
                    .value(INITIAL_PLAYER_VALUE)
                    .position(position)
                    .build()
        ).collect(Collectors.toList());
    }

    private List<String> generatePlayerPositions() {
        List<String> playersPositions = new ArrayList<>();

        playersPositions.addAll(Collections.nCopies(PlayerPosition.GOALKEEPER.getQuantity(), PlayerPosition.GOALKEEPER.toString()));
        playersPositions.addAll(Collections.nCopies(PlayerPosition.DEFENDER.getQuantity(), PlayerPosition.DEFENDER.toString()));
        playersPositions.addAll(Collections.nCopies(PlayerPosition.MIDFIELDER.getQuantity(), PlayerPosition.MIDFIELDER.toString()));
        playersPositions.addAll(Collections.nCopies(PlayerPosition.ATTACKER.getQuantity(), PlayerPosition.ATTACKER.toString()));

        return playersPositions;
    }

    private static int calculateQuantityOfPlayers() {
        return Arrays.stream(PlayerPosition.values())
                .map(PlayerPosition::getQuantity)
                .reduce(0, Integer::sum);
    }

    private void logRequest(SignupRequest signUpRequest) {
        log.info(String.format("User sign up: %s", signUpRequest));
    }
}
