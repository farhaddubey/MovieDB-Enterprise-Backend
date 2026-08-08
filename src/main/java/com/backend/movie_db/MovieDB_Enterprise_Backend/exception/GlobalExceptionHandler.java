package com.backend.movie_db.MovieDB_Enterprise_Backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<?> handleMovieNotFound(MovieNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "message", ex.getMessage(),
                        "status", 404
                    )
                );
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<?> handleReviewNotFound(ReviewNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "message", ex.getMessage(),
                        "status", 404
                        )
                );
    }
}

// Without global Exception handler users get 500 Internal Server Error
// user gets userless error