package com.example.ticketmanagement.dto;

import java.time.LocalDateTime;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private String text;
    private LocalDateTime createdAt;
    private Long userId;    // ✅ Adăugat userId pentru asociere
    private Long ticketId;  // ✅ Adăugat ticketId pentru asociere
}
