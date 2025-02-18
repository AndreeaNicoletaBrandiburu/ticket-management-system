package com.example.ticketmanagement.dto;

import com.example.ticketmanagement.model.TicketStatus;
import java.time.LocalDateTime;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private TicketStatus status;
    private Long userId;      // 🔹 Adăugat pentru referință la User
    private Long categoryId;  // 🔹 Adăugat pentru referință la Category
}
