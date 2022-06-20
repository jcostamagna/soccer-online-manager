package com.jcostamagna.socceronlinemanager.adapters.domain.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PlayerRequest {
    @NotBlank(message = "firstName must not be blank")
    private String firstName;

    @NotBlank(message = "lastName must not be blank")
    private String lastName;

    @NotBlank(message = "country must not be blank")
    private String country;
}
