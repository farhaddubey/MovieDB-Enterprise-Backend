package com.backend.movie_db.MovieDB_Enterprise_Backend.repository;

import com.backend.movie_db.MovieDB_Enterprise_Backend.model.Movie;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendationRepository extends JpaRepository {

    List<Movie> findTop20ByActiveTrueOrderByAverageRatingDesc();

    List<Movie> findTop20GenreAndActiveTrueOrderByAverageRatingDesc(String genre);
}
