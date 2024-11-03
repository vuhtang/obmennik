package org.highload.model.service;

import jakarta.persistence.*;
import lombok.Data;
import org.highload.model.User;

@Data
@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "status_id")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "theme_id")
    @Enumerated(EnumType.STRING)
    private Theme theme;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "responsible_id")
    private User responsible;

    @ManyToOne
    @JoinColumn(name = "complainer_id")
    private User complainer;
}
