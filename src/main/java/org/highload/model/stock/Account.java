package org.highload.model.stock;

import jakarta.persistence.*;
import lombok.Data;
import org.highload.model.User;
import org.highload.model.roles.UserRole;

import java.util.Set;

@Data
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "account")
    private Set<Wallet> wallets;
}
