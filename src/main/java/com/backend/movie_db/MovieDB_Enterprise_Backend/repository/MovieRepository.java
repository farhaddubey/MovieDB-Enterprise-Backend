package com.backend.movie_db.MovieDB_Enterprise_Backend.repository;

import com.backend.movie_db.MovieDB_Enterprise_Backend.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Page<Movie> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Movie> findByGenreIgnoreCase(String genre, Pageable pageable);

    Page<Movie> findByActiveTrue(Pageable pageable); 

    @Query("""SELECT m FROM Movie m WHERE m.totalRatings >= 100 ORDER BY m.averageRating DESC""")
    List<Movie> findTopRatedMovies;

    @Query("""SELECT m FROM Movie m WHERE m.active = true ORDER BY m.totalRatings DESC""")
    List<Movie> findTrendingMovies(Pageable pageable);
}

// Repository :
// Because it gives :
// CRUD
// Pagination
// Sorting
// Custom Queries
// Transactions
