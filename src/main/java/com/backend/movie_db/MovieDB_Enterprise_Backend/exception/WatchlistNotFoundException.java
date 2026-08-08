package com.backend.movie_db.MovieDB_Enterprise_Backend.exception;

public class WatchlistNotFoundException extends RuntimeException{

    public WatchlistNotFoundException(Long movieId) {
        super("Movie not found in watchlist : " + movieId);
    }
}
