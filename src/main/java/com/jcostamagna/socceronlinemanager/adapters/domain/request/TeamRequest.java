package com.jcostamagna.socceronlinemanager.adapters.domain.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TeamRequest {
    @NotBlank(message = "name must not be blank")
    private String name;

    @NotBlank(message = "country must not be blank")
    private String country;
}
