package org.highload.model;

import jakarta.persistence.*;
import lombok.Data;
import org.highload.common.model.Account;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

@Data
@Table(name = "fiat_wallet")
public class FiatWallet implements Persistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "balance")
    private Long balance;

    @org.springframework.data.annotation.Transient
    private Boolean isUserNew = false;

    @Override
    public boolean isNew() {
        return isUserNew == true;
    }
    @Override
    public Long getId(){
        return id;
    }
}
