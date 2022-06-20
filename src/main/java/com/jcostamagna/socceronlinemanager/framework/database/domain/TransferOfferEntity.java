package com.jcostamagna.socceronlinemanager.framework.database.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "transfer_market")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
public class TransferOfferEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "price", nullable = false)
    private int price;

    @OneToOne
    @JoinColumn(name = "player_id")
    private PlayerEntity player;

    public TransferOfferEntity(PlayerEntity player, int price) {
        this.player = player;
        this.price = price;
    }
}