package org.highload.model.stock;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "coin_price")
public class CoinPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "coin_id")
    private Coin coin;

    @Column(name = "price_dollars")
    private Integer priceInDollars;
}
