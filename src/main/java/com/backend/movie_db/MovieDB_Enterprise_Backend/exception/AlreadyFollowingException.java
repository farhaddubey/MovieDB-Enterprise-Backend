package com.backend.movie_db.MovieDB_Enterprise_Backend.exception;

public class AlreadyFollowingException extends RuntimeException{

    public AlreadyFollowingException(Long actorId) {
        super("Already following actor : " + actorId);
    }
}
