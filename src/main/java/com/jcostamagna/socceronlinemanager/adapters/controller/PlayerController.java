package com.jcostamagna.socceronlinemanager.adapters.controller;

import com.jcostamagna.socceronlinemanager.adapters.domain.request.PlayerRequest;
import com.jcostamagna.socceronlinemanager.adapters.utils.RequestValidator;
import com.jcostamagna.socceronlinemanager.framework.database.domain.PlayerEntity;
import com.jcostamagna.socceronlinemanager.usecases.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PlayerController {

    @Autowired
    PlayerService playerService;

    private final RequestValidator requestValidator = new RequestValidator();

    @PutMapping(path = {"/players/{id}"})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PlayerEntity> updatePlayerInformation(@PathVariable("id") long id, @Valid @RequestBody PlayerRequest player, Errors errors) {
        this.requestValidator.validateRequest(errors);

        return ResponseEntity.ok(playerService.update(id, player));
    }
}
