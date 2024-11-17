package org.highload.model.stock;

import jakarta.persistence.*;
import lombok.Data;
import org.highload.model.User;

import java.sql.Date;

@Data
@Entity
@Table(name = "coin_buy_history")
public class CoinBuyHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "coin_buy_id")
    private Coin coinBuy;

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
