package com.example.ticketmanagement.service;

import com.example.ticketmanagement.dto.NotificationDTO;
import com.example.ticketmanagement.model.Notification;
import com.example.ticketmanagement.model.User;
import com.example.ticketmanagement.repository.NotificationRepository;
import com.example.ticketmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Adăugare notificare corectată
    public NotificationDTO addNotification(NotificationDTO notificationDTO, Long userId) {
        // ✅ Verifică dacă utilizatorul există
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ Convertim DTO în entitate
        Notification notification = mapToEntity(notificationDTO, user);
        Notification savedNotification = notificationRepository.save(notification);

        // ✅ Returnăm DTO-ul rezultat
        return mapToDTO(savedNotification);
    }

    // ✅ Obținere notificare după ID
    public Optional<NotificationDTO> getNotificationById(Long id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        return notification.map(this::mapToDTO);
    }

    // ✅ Obținere toate notificările
    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Ștergere notificare
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    // ✅ Conversie DTO -> Entity
    private Notification mapToEntity(NotificationDTO notificationDTO, User user) {
        return Notification.builder()
                .id(notificationDTO.getId())
                .message(notificationDTO.getMessage())
                .createdAt(notificationDTO.getCreatedAt() != null ? notificationDTO.getCreatedAt() : LocalDateTime.now()) // ✅ Evită null
                .user(user) // ✅ Corectat: Asociem notificarea cu utilizatorul
                .build();
    }

    // ✅ Conversie Entity -> DTO
    private NotificationDTO mapToDTO(Notification notification) {
        return NotificationDTO.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .createdAt(notification.getCreatedAt())
                .userId(notification.getUser() != null ? notification.getUser().getId() : null) // ✅ Convertim user în userId
                .build();
    }
}
