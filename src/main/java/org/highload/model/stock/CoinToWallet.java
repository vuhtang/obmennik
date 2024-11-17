package org.highload.model.stock;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "coin_to_wallet")
public class CoinToWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "coin_id")
    private Coin coin;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @Column(name = "amount")
    private Long amount;
}
