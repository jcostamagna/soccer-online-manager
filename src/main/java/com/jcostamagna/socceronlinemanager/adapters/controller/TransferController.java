package com.jcostamagna.socceronlinemanager.adapters.controller;

import com.jcostamagna.socceronlinemanager.adapters.domain.request.BuyPlayerRequest;
import com.jcostamagna.socceronlinemanager.adapters.domain.request.SetToTransferRequest;
import com.jcostamagna.socceronlinemanager.adapters.utils.RequestValidator;
import com.jcostamagna.socceronlinemanager.framework.database.domain.TransferOfferEntity;
import com.jcostamagna.socceronlinemanager.usecases.TransferMarketService;
import com.jcostamagna.socceronlinemanager.usecases.ports.TransferMarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/transfers")
public class TransferController {

    @Autowired
    TransferMarketRepository transferMarketRepository;

    @Autowired
    TransferMarketService transferMarketService;

    RequestValidator requestValidator = new RequestValidator();

    @GetMapping()
    public List<TransferOfferEntity> getTransfers() {
        return transferMarketService.getAllTransfers();
    }

    @PostMapping(path = {"/set_player"})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TransferOfferEntity> setPlayerToTransfer(@Valid @RequestBody SetToTransferRequest setToTransferRequest, Errors errors) {
        this.requestValidator.validateRequest(errors);

        return ResponseEntity.ok(transferMarketService.setPlayerToTransfer(setToTransferRequest));
    }

    @PostMapping(path = {"/buy_player"})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TransferOfferEntity> buyPlayer(@Valid @RequestBody BuyPlayerRequest buyPlayerRequest, Errors errors) {
        this.requestValidator.validateRequest(errors);

        return ResponseEntity.ok(transferMarketService.buyPlayer(buyPlayerRequest));
    }
}
