package com.backend.movie_db.MovieDB_Enterprise_Backend.exception;

public class WatchListAlreadyExistsException extends RuntimeException{

    public WatchListAlreadyExistsException (Long movieId) {
        super("Watchlist exists." + movieId);
    }
}
