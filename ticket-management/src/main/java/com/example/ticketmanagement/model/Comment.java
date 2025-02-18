package com.example.ticketmanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false) // ✅ Relație corectă cu Ticket
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // ✅ Relație corectă cu User
    private User user;

    private LocalDateTime createdAt = LocalDateTime.now();
}
