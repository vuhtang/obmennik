package org.highload.model.stock;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "stock_account")
public class StockAccountBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "coin_id")
    private Coin coin;

    @Column(name = "amount")
    private Integer amount;
}
