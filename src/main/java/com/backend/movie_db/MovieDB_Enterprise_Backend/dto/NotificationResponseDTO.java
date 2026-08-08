package com.backend.movie_db.MovieDB_Enterprise_Backend.dto;

import com.backend.movie_db.MovieDB_Enterprise_Backend.model.NotificationType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponseDTO {

    private Long notificationId;

    private String title;

    private String message;

    private NotificationType notificationType;

    private Boolean isRead;

    private LocalDateTime createdAt;
}
