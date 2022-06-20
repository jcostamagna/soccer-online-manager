package com.jcostamagna.socceronlinemanager.framework.database.repository.JPARepositories;

import com.jcostamagna.socceronlinemanager.framework.database.domain.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamJPARepository extends JpaRepository<TeamEntity, Long> {

    boolean existsByName(String name);
}
