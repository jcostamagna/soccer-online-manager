package com.jcostamagna.socceronlinemanager.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Team {
    private long id;
    private String name;
    private String country;
    private int value;
    private int budget;
    private List<Player> players;
}
