package com.backend.movie_db.MovieDB_Enterprise_Backend.repository;

import com.backend.movie_db.MovieDB_Enterprise_Backend.model.CustomListMovie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomListMovieRepository extends JpaRepository<CustomListMovieRepository, Long> {

    Optional<CustomListMovie> findByCustomListIdAndMovieId(Long listId, Long movieId);

    List<CustomListMovie> findByCustomListId(Long listId);

    long countByCustomListId(Long listId);

    void deleteByCustomListId(Long listId);
}
