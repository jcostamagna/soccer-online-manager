package com.jcostamagna.socceronlinemanager.usecases.ports;

import com.jcostamagna.socceronlinemanager.domain.TransferOffer;
import com.jcostamagna.socceronlinemanager.framework.database.domain.TransferOfferEntity;

import java.util.List;
import java.util.Optional;

public interface TransferMarketRepository {
    List<TransferOfferEntity> findAll();

    boolean existsByPlayer(Long player);

    TransferOfferEntity save(TransferOffer transferOffer);

    Optional<TransferOfferEntity> findByPlayer(Long player);

    void delete(long id);
}
