package com.jcostamagna.socceronlinemanager.usecases;

import com.jcostamagna.socceronlinemanager.adapters.domain.request.LoginRequest;
import com.jcostamagna.socceronlinemanager.framework.configuration.security.UserDetailsImpl;
import com.jcostamagna.socceronlinemanager.framework.database.domain.UserEntity;
import com.jcostamagna.socceronlinemanager.usecases.ports.AuthenticationManagerService;
import com.jcostamagna.socceronlinemanager.usecases.ports.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    AuthenticationManagerService authenticationManagerService;

    @Autowired
    UserRepository userRepository;

    public UserDetailsImpl authenticateUser(LoginRequest loginRequest) {
        return authenticationManagerService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
    }

    public ResponseCookie generateCookie(UserDetailsImpl userDetails) {
        return authenticationManagerService.generateJwtCookie(userDetails);
    }

    public ResponseCookie logoutUser() {
        return authenticationManagerService.getCleanJwtCookie();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}
