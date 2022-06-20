package com.jcostamagna.socceronlinemanager.usecases;

import com.jcostamagna.socceronlinemanager.domain.Player;
import com.jcostamagna.socceronlinemanager.domain.faker.FakerGeneratorImpl;
import com.jcostamagna.socceronlinemanager.domain.validator.CountryValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class TeamGeneratorTest extends TeamGenerator {

    private final List<String> positionsArrays = List.of(
            "Goalkeeper", "Goalkeeper", "Goalkeeper",
            "Defender", "Defender", "Defender", "Defender", "Defender", "Defender",
            "Midfielder", "Midfielder", "Midfielder", "Midfielder", "Midfielder", "Midfielder",
            "Attacker", "Attacker", "Attacker", "Attacker", "Attacker");

    @BeforeEach
    void setUp() {
        this.faker = new FakerGeneratorImpl();
        this.countryValidator = new CountryValidatorImpl();
    }

    @Test
    public void test_generate_players_method_positions_are_correct() {
        // GIVEN
        List<Player> players = this.generatePlayers();

        // THEN
        assertThat(players.stream().map(Player::getPosition).collect(Collectors.toList()))
                .isEqualTo(positionsArrays);
    }

    @Test
    public void test_calculate_team_initial_value() {
        // THEN
        assertThat(INITAL_TEAM_VALUE)
                .isEqualTo(20000000);
    }
}