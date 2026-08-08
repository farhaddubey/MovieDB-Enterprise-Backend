package com.backend.movie_db.MovieDB_Enterprise_Backend.exception;

public class ActorNotFoundException extends RuntimeException{

    public ActorNotFoundException(Long actorId) {
        super("Actor Not found." + actorId);
    }
}
