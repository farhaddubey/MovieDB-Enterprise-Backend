package com.backend.movie_db.MovieDB_Enterprise_Backend.repository;

import com.backend.movie_db.MovieDB_Enterprise_Backend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByMovieIdOrderByCreatedAtDesc(Long movieId);

    List<Review> findByIdAndMovieId(Long userId, Long movieId);

    List<Review> findByUserAndMovieId(Long userId, Long movieId);

    long countByMovieId(Long movieId);

    Optional<Review> findByUserIdAndMovieId(Long userId, Long movieId);

    List<Review> findByMovieId(Long movieId); 
}
