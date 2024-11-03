package org.highload.model.stock;

import jakarta.persistence.*;
import lombok.Data;
import org.highload.model.User;

@Data
@Entity
@Table(name = "coin_exchange_history")
public class CoinExchangeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "coin_id_sell")
    private Coin coinSell;

    @ManyToOne
    @JoinColumn(name = "coin_id_buy")
    private Coin coinBuy;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "tax")
    private Integer tax;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User user;
}
