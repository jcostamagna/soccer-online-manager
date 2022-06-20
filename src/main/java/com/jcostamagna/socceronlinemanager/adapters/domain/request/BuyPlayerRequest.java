package com.jcostamagna.socceronlinemanager.adapters.domain.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class BuyPlayerRequest {
    @NotNull(message = "Player id must not be null")
    @Min(value = 0, message = "Player id must be positive")
    private Long player;
}
