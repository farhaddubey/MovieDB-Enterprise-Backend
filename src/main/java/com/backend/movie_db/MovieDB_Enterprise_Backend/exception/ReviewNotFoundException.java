package com.backend.movie_db.MovieDB_Enterprise_Backend.exception;

public class ReviewNotFoundException extends RuntimeException {

    public ReviewNotFoundException(Long reviewId) {
        // Here only string is passed and the status addtion will be done in GlobalExceptionHandler
        super("Review not found with id: " + reviewId);
    }
}
