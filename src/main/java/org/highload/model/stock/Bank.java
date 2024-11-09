//package org.highload.model.stock;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.util.Set;
//
//@Data
//@Entity
//@Table(name = "bank")
//public class Bank {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @Column(name = "name")
//    private String name;
//
//    @OneToMany(mappedBy = "bank")
//    private Set<BankAccount> bankAccounts;
//}
