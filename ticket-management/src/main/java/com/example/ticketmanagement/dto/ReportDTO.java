package com.example.ticketmanagement.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {
    private Long id;
    private String type;
    private LocalDateTime createdAt;
}
