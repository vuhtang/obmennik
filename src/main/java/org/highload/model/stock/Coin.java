package org.highload.model.stock;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "coin")
public class Coin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;
}
