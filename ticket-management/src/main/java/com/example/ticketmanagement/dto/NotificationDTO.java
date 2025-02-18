package com.example.ticketmanagement.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private Long id;
    private String message;
    private LocalDateTime createdAt;
    private Long userId;  // ✅ Adăugat userId pentru a asocia notificarea cu un utilizator
}
