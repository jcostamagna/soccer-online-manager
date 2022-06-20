package com.jcostamagna.socceronlinemanager.adapters.controller;

import com.jcostamagna.socceronlinemanager.adapters.domain.request.LoginRequest;
import com.jcostamagna.socceronlinemanager.adapters.domain.request.SignupRequest;
import com.jcostamagna.socceronlinemanager.adapters.utils.RequestValidator;
import com.jcostamagna.socceronlinemanager.framework.configuration.security.UserDetailsImpl;
import com.jcostamagna.socceronlinemanager.framework.database.domain.UserEntity;
import com.jcostamagna.socceronlinemanager.usecases.TeamGenerator;
import com.jcostamagna.socceronlinemanager.usecases.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    TeamGenerator teamGenerator;

    @Autowired
    UserService userService;

    RequestValidator requestValidator = new RequestValidator();

    @PostMapping(path = {"/sign_up"})
    public ResponseEntity<UserEntity> userSignUp(@Valid @RequestBody SignupRequest signUpRequest, Errors errors) {
        this.requestValidator.validateRequest(errors);

        return ResponseEntity.ok(teamGenerator.generateTeam(signUpRequest));
    }

    @PostMapping("/log_in")
    public ResponseEntity<UserDetailsImpl> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        UserDetailsImpl userDetails = userService.authenticateUser(loginRequest);

        ResponseCookie cookie = userService.generateCookie(userDetails);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(userDetails);
    }

    @GetMapping()
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/log_out")
    public ResponseEntity<String> logoutUser() {
        ResponseCookie cookie = userService.logoutUser();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("You've been signed out!");
    }
}
