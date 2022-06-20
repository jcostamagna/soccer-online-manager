package com.jcostamagna.socceronlinemanager.framework.database.repository;

import com.jcostamagna.socceronlinemanager.domain.Player;
import com.jcostamagna.socceronlinemanager.domain.Team;
import com.jcostamagna.socceronlinemanager.domain.User;
import com.jcostamagna.socceronlinemanager.framework.database.domain.PlayerEntity;
import com.jcostamagna.socceronlinemanager.framework.database.domain.TeamEntity;
import com.jcostamagna.socceronlinemanager.framework.database.domain.UserEntity;
import com.jcostamagna.socceronlinemanager.framework.database.repository.JPARepositories.UserJPARepository;
import com.jcostamagna.socceronlinemanager.usecases.ports.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserEntityRepositoryImpl implements UserRepository {

    @Autowired
    UserJPARepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserEntity save(User user) {
        Team team = user.getTeam();

        TeamEntity teamEntity = new TeamEntity(team.getName(), team.getCountry(), team.getValue(), team.getBudget());

        team.getPlayers().stream().map(this::mapPlayer).forEach(teamEntity::addPlayer);

        UserEntity userEntity = new UserEntity(user.getEmail(),
                encoder.encode(user.getPassword()));

        userEntity.setTeam(teamEntity);

        return userRepository.save(userEntity);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    private PlayerEntity mapPlayer(Player player) {
        return new PlayerEntity(
                player.getFirstName(),
                player.getLastName(),
                player.getPosition(),
                player.getCountry(),
                player.getAge(),
                player.getValue()
        );
    }
}
