package org.highload.model;

import jakarta.persistence.*;
import lombok.Data;
import org.highload.common.model.Account;

@Data
@Entity
@Table(name = "fiat_wallet")
public class FiatWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "balance")
    private Long balance;
}
