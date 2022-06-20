package com.jcostamagna.socceronlinemanager.framework.database.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "teams")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
public class TeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "value", nullable = false)
    private int value;

    @Column(name = "budget", nullable = false)
    private int budget;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<PlayerEntity> players;

    public TeamEntity(String name, String country, int value, int budget) {
        this.name = name;
        this.country = country;
        this.value = value;
        this.budget = budget;
    }

    public void addPlayer(PlayerEntity playerEntity) {
        if (players == null) {
            players = new ArrayList<>();
        }
        players.add(playerEntity);

        playerEntity.setTeam(this);
    }
}
