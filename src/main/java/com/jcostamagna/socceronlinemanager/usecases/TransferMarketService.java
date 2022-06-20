package com.jcostamagna.socceronlinemanager.usecases;

import com.jcostamagna.socceronlinemanager.adapters.controller.exceptions.BadRequestException;
import com.jcostamagna.socceronlinemanager.adapters.domain.request.BuyPlayerRequest;
import com.jcostamagna.socceronlinemanager.adapters.domain.request.SetToTransferRequest;
import com.jcostamagna.socceronlinemanager.domain.Player;
import com.jcostamagna.socceronlinemanager.domain.TransferOffer;
import com.jcostamagna.socceronlinemanager.domain.random.RandomNumberGenerator;
import com.jcostamagna.socceronlinemanager.domain.random.RandomNumberGeneratorImpl;
import com.jcostamagna.socceronlinemanager.framework.database.domain.PlayerEntity;
import com.jcostamagna.socceronlinemanager.framework.database.domain.TeamEntity;
import com.jcostamagna.socceronlinemanager.framework.database.domain.TransferOfferEntity;
import com.jcostamagna.socceronlinemanager.framework.database.domain.UserEntity;
import com.jcostamagna.socceronlinemanager.usecases.ports.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log
@Service
public class TransferMarketService {
    @Autowired
    AuthenticationManagerService authenticationManagerService;

    @Autowired
    TransferMarketRepository transferMarketRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    TeamRepository teamRepository;

    protected RandomNumberGenerator randomNumberGenerator = new RandomNumberGeneratorImpl();

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public TransferOfferEntity setPlayerToTransfer(SetToTransferRequest setToTransferRequest) {
        this.logRequest(setToTransferRequest);

        UserEntity loggedUser = authenticationManagerService.getLoggedUser();

        this.applyPlayerValidations(setToTransferRequest, loggedUser);

        Player player = Player.builder().id(setToTransferRequest.getPlayer()).build();

        TransferOffer transferOffer = TransferOffer.builder()
                .player(player)
                .price(setToTransferRequest.getPrice())
                .build();

        return transferMarketRepository.save(transferOffer);
    }

    private void applyPlayerValidations(SetToTransferRequest setToTransferRequest, UserEntity loguedUser) {
        if (!this.isPlayerFromUser(loguedUser, setToTransferRequest.getPlayer())) {
            throw new BadRequestException("Player is not from user");
        }

        if (transferMarketRepository.existsByPlayer(setToTransferRequest.getPlayer())) {
            throw new BadRequestException("Player is already in the transfer list");
        }
    }

    private boolean isPlayerFromUser(UserEntity loguedUser, long playerId) {
        return loguedUser.getTeam()
                .getPlayers()
                .stream()
                .anyMatch(player -> player.getId() == playerId);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<TransferOfferEntity> getAllTransfers() {
        return this.transferMarketRepository.findAll();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public TransferOfferEntity buyPlayer(BuyPlayerRequest buyPlayerRequest) {
        this.logRequest(buyPlayerRequest);

        UserEntity loggedUser = authenticationManagerService.getLoggedUser();

        TransferOfferEntity transferOffer = transferMarketRepository.findByPlayer(buyPlayerRequest.getPlayer())
                .orElseThrow(() -> new BadRequestException("Player is not in transfer"));

        this.applyInitialValidations(buyPlayerRequest, loggedUser, transferOffer);

        PlayerEntity player = playerRepository.findPlayerById(buyPlayerRequest.getPlayer())
                .orElseThrow(() -> new BadRequestException("Player does not exists"));

        int budget = loggedUser.getTeam().getBudget();

        this.updateTeamSeller(transferOffer, budget, player);

        this.applyTransfer(loggedUser, transferOffer, player);

        transferMarketRepository.delete(transferOffer.getId());

        return transferOffer;
    }

    private void applyTransfer(UserEntity loguedUser, TransferOfferEntity transferOffer, PlayerEntity player) {
        TeamEntity teamBuyer = loguedUser.getTeam();

        Integer newPlayerValue = calculateNewPlayerValue(player.getValue());

        teamBuyer.setValue(teamBuyer.getValue() + newPlayerValue);
        teamBuyer.setBudget(teamBuyer.getBudget() - transferOffer.getPrice());

        player.setTeam(teamBuyer);
        player.setValue(newPlayerValue);

        playerRepository.save(player);
    }

    protected Integer calculateNewPlayerValue(int playerValue) {

        Double factor =  this.randomNumberGenerator.generateNumberBetween(10, 101) / 100.0 + 1;

        log.info(String.format("Factor value for player is: %s", factor));

        return Double.valueOf(playerValue * factor).intValue();
    }

    private void updateTeamSeller(TransferOfferEntity transferOffer, int budget, PlayerEntity player) {
        TeamEntity teamSeller = teamRepository.findById(player.getTeam().getId()).orElseThrow();

        teamSeller.setBudget(budget + transferOffer.getPrice());
        teamSeller.setValue(teamSeller.getValue() - player.getValue());

        teamRepository.save(teamSeller);
    }

    private void applyInitialValidations(BuyPlayerRequest buyPlayerRequest, UserEntity loguedUser, TransferOfferEntity transferOffer) {
        if (this.isPlayerFromUser(loguedUser, buyPlayerRequest.getPlayer())) {
            throw new BadRequestException("Player is from user");
        }

        if(loguedUser.getTeam().getBudget() < transferOffer.getPrice()) {
            throw new BadRequestException("User has less money than offer");
        }
    }

    private void logRequest(SetToTransferRequest setToTransferRequest) {
        log.info(String.format("Set to transfer: %s", setToTransferRequest));
    }

    private void logRequest(BuyPlayerRequest buyPlayerRequest) {
        log.info(String.format("Buy player: %s", buyPlayerRequest));
    }
}
