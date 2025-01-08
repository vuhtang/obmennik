package org.highload.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.highload.common.model.Account;

import java.util.Set;


@Data
@Entity
@Table(name = "wallet")
@NoArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "private_key")
    private String privateKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "wallet")
    private Set<CoinToWallet> coins;

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj instanceof Wallet) {
            return this.id.equals(((Wallet) obj).id);
        } else return false;
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }
}
