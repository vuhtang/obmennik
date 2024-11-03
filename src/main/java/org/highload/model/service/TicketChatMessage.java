package org.highload.model.service;

import jakarta.persistence.*;
import lombok.Data;
import org.highload.model.User;

import java.sql.Date;

@Data
@Entity
@Table(name = "ticket_chat_message")
public class TicketChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Column(name = "create_dttm")
    private Date createDttm;

    @Column(name = "message_text")
    private String message;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
}
