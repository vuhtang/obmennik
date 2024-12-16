package org.highload.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "coin_exchange_history")
public class CoinExchangeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "coin_sell_id")
    private Coin coinSell;

    @ManyToOne
    @JoinColumn(name = "coin_buy_id")
    private Coin coinBuy;

    @Column(name = "coin_sell_amount")
    private Long coinSellAmount;

    @Column(name = "coin_sell_price")
    private Long coinSellPrice;

    @Column(name = "coin_buy_amount")
    private Long coinBuyAmount;

    @Column(name = "coin_buy_price")
    private Long coinBuyPrice;

    @Column(name = "tax")
    private Integer tax;

    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet user;
}
