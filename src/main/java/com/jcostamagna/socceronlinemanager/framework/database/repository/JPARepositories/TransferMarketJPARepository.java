package com.jcostamagna.socceronlinemanager.framework.database.repository.JPARepositories;

import com.jcostamagna.socceronlinemanager.framework.database.domain.TransferOfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransferMarketJPARepository extends JpaRepository<TransferOfferEntity, Long> {

    boolean existsByPlayer_Id(Long playerId);

    Optional<TransferOfferEntity> findByPlayer_Id(Long player);
}
