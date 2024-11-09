//package org.highload.model.stock;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Entity
//@Data
//@Table(name = "dollar_exchange_history")
//public class DollarExchangeHistory {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @ManyToOne
//    @JoinColumn(name = "wallet_id")
//    private Wallet wallet;
//
//    @ManyToOne
//    @JoinColumn(name = "bank_account_id")
//    private BankAccount bankAccount;
//
//    @Column(name = "amount")
//    private Integer amount;
//}
