package com.backend.movie_db.MovieDB_Enterprise_Backend.repository;

import com.backend.movie_db.MovieDB_Enterprise_Backend.model.CustomList;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.CustomListMovie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomListRepository extends JpaRepository<CustomList, Long> {

    List<CustomList> findByUserIdOrderByCreatedAtDesc(Long userId);
}
