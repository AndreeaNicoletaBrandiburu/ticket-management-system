package com.example.ticketmanagement.controller;

import com.example.ticketmanagement.dto.NotificationDTO;
import com.example.ticketmanagement.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // ✅ Adăugare notificare corectată
    @PostMapping("/{userId}")
    public ResponseEntity<NotificationDTO> createNotification(
            @PathVariable Long userId,
            @RequestBody NotificationDTO notificationDTO) {

        NotificationDTO newNotification = notificationService.addNotification(notificationDTO, userId);
        return ResponseEntity.ok(newNotification);
    }

    // ✅ Obținere notificare după ID
    @GetMapping("/{id}")
    public ResponseEntity<NotificationDTO> getNotificationById(@PathVariable Long id) {
        Optional<NotificationDTO> notification = notificationService.getNotificationById(id);
        return notification.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Obținere toate notificările
    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    // ✅ Ștergere notificare
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}
