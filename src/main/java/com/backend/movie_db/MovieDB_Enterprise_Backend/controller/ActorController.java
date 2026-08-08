package com.backend.movie_db.MovieDB_Enterprise_Backend.controller;

import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.ActorResponseDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actors")
@RequiredArgsConstructor
public class ActorController {

    private final ActorService actorService;

    @PostMapping("/{actorId}/follow")
    public ResponseEntity<ActorResponseDTO> followActor(@PathVariable Long actorId, @RequestParam Long userId) {
        return ResponseEntity.ok(actorService.followActor(userId, actorId));
    }

    @DeleteMapping("/{actorId}/follow")
    public ResponseEntity<String> unfollowActor(@PathVariable Long actorId, @RequestParam Long userId) {
        actorService.unfollowActor(actorId, userId);
        return ResponseEntity.ok("Deleted successfully");
    }

    @GetMapping("/following")
    public ResponseEntity<List<ActorResponseDTO>> getFollowedActors(@RequestParam Long userId) {
        return ResponseEntity.ok(actorService.getFollowedActors(userId));
    }

    @GetMapping("/{actorId}/followers")
    public ResponseEntity<Long> getFollowerCount(@PathVariable Long actorId) {
        return ResponseEntity.ok(actorService.getFollowerCount(actorId));
    }

    @GetMapping("/{actorId}/status")
    public ResponseEntity<Boolean> isFollowing(@RequestParam Long userId, @PathVariable Long actorId) {
        return ResponseEntity.ok(actorService.isFollowing(userId, actorId));
    }

    @GetMapping("/popular")
    public ResponseEntity<List<ActorResponseDTO>> getPopularActors() {
        return ResponseEntity.ok(actorService.getPopularActors());
    }
}
