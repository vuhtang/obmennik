package org.highload.model;

import jakarta.persistence.*;
import lombok.Data;
import org.highload.model.roles.UserRole;
import org.highload.model.stock.Account;

import java.sql.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "customer")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "surname")
    private String surname;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "dob")
    private Date dateOfBirth;

    @ManyToMany
    @JoinTable(
            name = "customer_to_role",
            joinColumns = { @JoinColumn(name = "customer_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    private Set<UserRole> roles;

    @OneToMany(mappedBy = "user")
    private Set<Account> accounts;
}