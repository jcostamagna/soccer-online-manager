package com.jcostamagna.socceronlinemanager.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Player {
    private long id;
    private String firstName;
    private String lastName;
    private String position;
    private String country;
    private int age;
    private int value;
    private Team team;
}
