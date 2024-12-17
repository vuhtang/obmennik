package org.highload.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "coin")
public class Coin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
}
