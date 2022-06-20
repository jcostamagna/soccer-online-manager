package com.jcostamagna.socceronlinemanager.adapters.domain.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank(message = "email must not be blank")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "password must not be blank")
    private String password;
}