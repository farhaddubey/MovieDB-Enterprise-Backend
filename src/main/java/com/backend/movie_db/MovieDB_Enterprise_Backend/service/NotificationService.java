package com.backend.movie_db.MovieDB_Enterprise_Backend.service;

import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.NotificationResponseDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.Notification;
import com.backend.movie_db.MovieDB_Enterprise_Backend.repository.NotificationRepository;
import com.backend.movie_db.MovieDB_Enterprise_Backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {
    private final NotificationRepository notificationRepository;

    private final UserRepository userRepository;

    private NotificationResponseDTO mapToResponse(Notification notification) {
        return NotificationResponseDTO
                .builder()
                .notificationId(notification.getId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .notificationType(notification.getNotificationType())
                .isRead(notification.getIsRead())
                .createdAt(notification.getCreatedAt())
                .build(); 
    }
}
