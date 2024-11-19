package org.highload.model.stock;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.highload.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account")
//    private List<Wallet> wallets = new ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj instanceof Account) {
            return this.id.equals(((Account) obj).id);
        } else return false;
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }
}
