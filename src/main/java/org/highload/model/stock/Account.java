package org.highload.model.stock;

import jakarta.persistence.*;
import lombok.Data;
import org.highload.model.User;

import java.util.Set;

@Data
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "account")
    private Set<Wallet> wallets;
}
