package org.highload.model.stock;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Set;


@Data
@Entity
@Table(name = "wallet")
@Builder
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String privateKey;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "wallet")
    private Set<CoinToWallet> coins;
}
