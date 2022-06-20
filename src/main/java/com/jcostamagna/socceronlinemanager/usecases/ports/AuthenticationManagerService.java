package com.jcostamagna.socceronlinemanager.usecases.ports;

import com.jcostamagna.socceronlinemanager.framework.configuration.security.UserDetailsImpl;
import com.jcostamagna.socceronlinemanager.framework.database.domain.UserEntity;
import org.springframework.http.ResponseCookie;

public interface AuthenticationManagerService {
    UserDetailsImpl authenticateUser(String email, String password);

    ResponseCookie generateJwtCookie(UserDetailsImpl userDetails);

    ResponseCookie getCleanJwtCookie();

    UserEntity getLoggedUser();
}
