package com.backend.movie_db.MovieDB_Enterprise_Backend.exception;

public class ReviewOwnershipException extends RuntimeException{

    public ReviewOwnershipException() {
        super("You are not owner of this review.");
    }
}
