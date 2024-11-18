package org.highload.model.roles;

import jakarta.persistence.*;
import lombok.Data;
import org.highload.model.User;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToMany
    @JoinTable(
            name = "role_to_access",
            joinColumns = { @JoinColumn(name = "role_id") },
            inverseJoinColumns = { @JoinColumn(name = "access_id") }
    )
    private Set<ControlAccess> accesses;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();
}
