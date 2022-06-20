package com.jcostamagna.socceronlinemanager.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String email;
    private String password;
    private Team team;
}


