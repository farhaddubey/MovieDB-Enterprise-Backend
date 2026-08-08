package com.backend.movie_db.MovieDB_Enterprise_Backend.repository;

import com.backend.movie_db.MovieDB_Enterprise_Backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name); 
}
