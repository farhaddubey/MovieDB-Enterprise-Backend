package com.backend.movie_db.MovieDB_Enterprise_Backend.service;

import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.ActorResponseDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.exception.UserNotFoundException;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.Actor;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.ActorFollow;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.User;
import com.backend.movie_db.MovieDB_Enterprise_Backend.repository.ActorFollowRepository;
import com.backend.movie_db.MovieDB_Enterprise_Backend.repository.ActorRepository;
import com.backend.movie_db.MovieDB_Enterprise_Backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ActorService {

    private final AuthService authService;

    private final ActorFollowRepository actorFollowRepository;

    private final ActorRepository actorRepository;

    private final UserRepository userRepository;

    private ActorResponseDTO mapToResponse(Actor actor) {
        long followers = actorFollowRepository.countByActorId(actor.getId());
        return ActorResponseDTO.builder()
                .actorId(actor.getId())
                .name(actor.getName())
                .profileImage(actor.getProfileImage())
                .nationality(actor.getNationality())
                .followCount(followers)
                .build();
    }

    public ActorResponseDTO followActor(Long userId, Long actorId) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new UserNotFoundException(userId)
                );
        Actor actor = actorRepository.findById(actorId)
                .orElseThrow(
                        () -> new RuntimeException("Actor Not found.")
                );
        boolean alreadyFollowing = actorFollowRepository.existsByUserIdAndActorId(userId, actorId);

        if (alreadyFollowing) {
            return mapToResponse(actor);
        }

        ActorFollow actorFollow = ActorFollow.builder()
                .user(user)
                .actor(actor)
                .build();
        actorFollowRepository.save(actorFollow);

        return mapToResponse(actor);
    }

    public void unfollowActor(Long userId, Long actorId) {
        ActorFollow follow = actorFollowRepository.followByUserIdAndActorId(userId, actorId)
                .orElseThrow(
                        () -> new RuntimeException("Follow relationship not found.")
                );
        actorFollowRepository.delete(follow);
    }

    public List<ActorResponseDTO> getFollowedActors(Long userId) {
        return actorFollowRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(ActorFollow::getActor)
                .map(this::mapToResponse)
                .toList();
    }

    public Long getFollowerCount(Long actorId) {
        return actorFollowRepository.countByActorId(actorId);
    }

    public boolean isFollowing(Long userId, Long actorId) {
        return actorFollowRepository.existsByUserIdAndActorId(userId, actorId);
    }

    public List<ActorResponseDTO> getPopularActors() {
        return actorRepository.findTop20ByOrderByIdDesc()
                .stream().map(this::mapToResponse).toList();
    }




}
