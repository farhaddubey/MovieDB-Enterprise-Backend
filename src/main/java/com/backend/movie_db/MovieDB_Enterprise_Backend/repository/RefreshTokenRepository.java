package com.backend.movie_db.MovieDB_Enterprise_Backend.repository;

import com.backend.movie_db.MovieDB_Enterprise_Backend.model.RefreshToken;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Ref;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);
}
