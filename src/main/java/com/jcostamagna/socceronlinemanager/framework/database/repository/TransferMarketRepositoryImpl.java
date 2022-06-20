package com.jcostamagna.socceronlinemanager.framework.database.repository;

import com.jcostamagna.socceronlinemanager.domain.Player;
import com.jcostamagna.socceronlinemanager.domain.TransferOffer;
import com.jcostamagna.socceronlinemanager.framework.database.domain.PlayerEntity;
import com.jcostamagna.socceronlinemanager.framework.database.domain.TransferOfferEntity;
import com.jcostamagna.socceronlinemanager.framework.database.repository.JPARepositories.PlayerJPARepository;
import com.jcostamagna.socceronlinemanager.framework.database.repository.JPARepositories.TransferMarketJPARepository;
import com.jcostamagna.socceronlinemanager.usecases.ports.TransferMarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TransferMarketRepositoryImpl implements TransferMarketRepository {

    @Autowired
    TransferMarketJPARepository transferMarketJPARepository;

    @Autowired
    PlayerJPARepository playerJPARepository;

    @Override
    public List<TransferOfferEntity> findAll() {
        return transferMarketJPARepository.findAll();
    }

    @Override
    public boolean existsByPlayer(Long player) {
        return transferMarketJPARepository.existsByPlayer_Id(player);
    }

    @Override
    public TransferOfferEntity save(TransferOffer transferOffer) {
        TransferOfferEntity transferOfferEntity = new TransferOfferEntity(
                this.mapPlayer(transferOffer.getPlayer()),
                transferOffer.getPrice()
        );

        return transferMarketJPARepository.save(transferOfferEntity);
    }

    @Override
    public Optional<TransferOfferEntity> findByPlayer(Long player) {
        return transferMarketJPARepository.findByPlayer_Id(player);
    }

    @Override
    public void delete(long id) {
        transferMarketJPARepository.deleteById(id);
    }

    private PlayerEntity mapPlayer(Player player) {
        return playerJPARepository.findById(player.getId()).orElseThrow();
    }
}
