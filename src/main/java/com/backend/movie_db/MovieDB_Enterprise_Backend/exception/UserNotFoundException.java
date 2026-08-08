package com.backend.movie_db.MovieDB_Enterprise_Backend.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id) {
        super("User not found with id : " + id);
    }
}
