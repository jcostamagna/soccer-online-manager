package com.jcostamagna.socceronlinemanager.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferOffer {
    private Player player;
    private int price;
}
