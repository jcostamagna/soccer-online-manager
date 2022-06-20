package com.jcostamagna.socceronlinemanager.adapters.controller;

import com.jcostamagna.socceronlinemanager.adapters.domain.request.TeamRequest;
import com.jcostamagna.socceronlinemanager.adapters.utils.RequestValidator;
import com.jcostamagna.socceronlinemanager.framework.database.domain.TeamEntity;
import com.jcostamagna.socceronlinemanager.framework.database.domain.UserEntity;
import com.jcostamagna.socceronlinemanager.usecases.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class TeamController {

    @Autowired
    TeamService teamService;

    RequestValidator requestValidator = new RequestValidator();

    @GetMapping(path = {"/teams"})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserEntity> getTeamInformation() {
        return ResponseEntity.ok(teamService.getTeamInformation());
    }

    @PutMapping(path = {"/teams/{id}"})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TeamEntity> updateTeamInformation(@PathVariable("id") long id, @Valid @RequestBody TeamRequest team, Errors errors) {
        this.requestValidator.validateRequest(errors);

        return ResponseEntity.ok(teamService.update(id, team));
    }
}
