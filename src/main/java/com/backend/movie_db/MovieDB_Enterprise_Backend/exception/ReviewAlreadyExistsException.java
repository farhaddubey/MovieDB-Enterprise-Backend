package com.backend.movie_db.MovieDB_Enterprise_Backend.exception;

public class ReviewAlreadyExistsException extends RuntimeException {

    public ReviewAlreadyExistsException(Long userId, Long movieId) {
        super("Review already exists for user" + userId + " and movie " + movieId);
    }
}
