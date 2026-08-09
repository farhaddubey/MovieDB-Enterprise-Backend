package com.backend.movie_db.MovieDB_Enterprise_Backend.exception;

public class NotificationNotFoundException extends RuntimeException{

    public NotificationNotFoundException(Long notificationId) {
        super("Notifications not found with id : " + notificationId);
    }
}
