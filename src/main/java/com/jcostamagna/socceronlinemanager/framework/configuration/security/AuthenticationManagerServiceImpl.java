package com.jcostamagna.socceronlinemanager.framework.configuration.security;

import com.jcostamagna.socceronlinemanager.framework.configuration.security.jwt.JwtUtils;
import com.jcostamagna.socceronlinemanager.framework.database.domain.UserEntity;
import com.jcostamagna.socceronlinemanager.usecases.ports.AuthenticationManagerService;
import com.jcostamagna.socceronlinemanager.usecases.ports.UserRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Log
@Component
public class AuthenticationManagerServiceImpl implements AuthenticationManagerService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public UserDetailsImpl authenticateUser(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return (UserDetailsImpl) authentication.getPrincipal();
    }

    @Override
    public ResponseCookie generateJwtCookie(UserDetailsImpl userDetails) {
        return jwtUtils.generateJwtCookie(userDetails);
    }

    @Override
    public ResponseCookie getCleanJwtCookie() {
        return jwtUtils.getCleanJwtCookie();
    }

    @Override
    public UserEntity getLoggedUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        log.info(String.format("User authenticated: %s", userDetails));

        return userRepository.findByEmail(userDetails.getEmail()).orElseThrow();
    }
}
