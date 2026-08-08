package com.backend.movie_db.MovieDB_Enterprise_Backend.repository;

import com.backend.movie_db.MovieDB_Enterprise_Backend.model.ActorFollow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActorFollowRepository extends JpaRepository<ActorFollow, Long> {

    Optional<ActorFollow> followByUserIdAndActorId(Long userId, Long actorId);

    boolean existsByUserIdAndActorId(Long userId, Long actorId);

    List<ActorFollow> findByUserIdOrderByCreatedAtDesc(Long userId);

    long countByActorId(Long actorId);
}
