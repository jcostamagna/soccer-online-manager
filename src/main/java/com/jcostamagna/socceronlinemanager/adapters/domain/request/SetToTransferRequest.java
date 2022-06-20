package com.jcostamagna.socceronlinemanager.adapters.domain.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class SetToTransferRequest {
    @NotNull(message = "Player id must not be null")
    @Min(value = 0, message = "Player id must be positive")
    private Long player;

    @NotNull(message = "Price must not be null")
    @Min(value = 1, message = "Price must be positive")
    private Integer price;
}
