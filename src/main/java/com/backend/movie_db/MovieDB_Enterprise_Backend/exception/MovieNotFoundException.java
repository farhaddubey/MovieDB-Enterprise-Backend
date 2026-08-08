package com.backend.movie_db.MovieDB_Enterprise_Backend.exception;

public class MovieNotFoundException extends RuntimeException{

    public MovieNotFoundException(Long movieId) {
        super("Movie not found with id : " + movieId);
    }
}
