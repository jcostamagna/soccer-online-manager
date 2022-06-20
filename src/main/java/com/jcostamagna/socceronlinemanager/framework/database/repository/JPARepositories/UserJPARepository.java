package com.jcostamagna.socceronlinemanager.framework.database.repository.JPARepositories;


import com.jcostamagna.socceronlinemanager.framework.database.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJPARepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Boolean existsByEmail(String email);
}