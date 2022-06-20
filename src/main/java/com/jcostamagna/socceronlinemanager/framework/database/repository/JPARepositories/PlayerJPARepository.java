package com.jcostamagna.socceronlinemanager.framework.database.repository.JPARepositories;

import com.jcostamagna.socceronlinemanager.framework.database.domain.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerJPARepository extends JpaRepository<PlayerEntity, Long> {
}
