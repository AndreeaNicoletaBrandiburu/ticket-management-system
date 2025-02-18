package com.example.ticketmanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // ✅ Relație corectă cu User
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false) // ✅ Relație corectă cu Category
    private Category category;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>(); // ✅ Evităm NullPointerException

    // ✅ Constructor pentru testare, fără lista de comentarii
    public Ticket(Long id, String title, String description, TicketStatus status, LocalDateTime createdAt, User user, Category category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.user = user;
        this.category = category;
        this.comments = new ArrayList<>(); // Inițializăm lista pentru a evita erori
    }
}
