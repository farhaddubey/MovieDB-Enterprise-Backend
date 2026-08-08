package com.backend.movie_db.MovieDB_Enterprise_Backend.repository;

import com.backend.movie_db.MovieDB_Enterprise_Backend.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {

    Optional<Watchlist> findByUserIdAndMovieId(Long userId, Long movieId);

    List<Watchlist> findByUserIdOrderByCreatedAtDesc(Long userId);

    boolean existsByUserAndMovieId(Long userId, Long movieId);

    long countByMovieId(Long movieId);
}
