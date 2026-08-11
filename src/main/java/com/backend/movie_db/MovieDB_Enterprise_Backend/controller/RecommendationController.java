package com.backend.movie_db.MovieDB_Enterprise_Backend.controller;

import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.RecommendationResponseDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/top-rated")
    public ResponseEntity<List<RecommendationResponseDTO>> getTopRatedRecommendations() {
        return ResponseEntity.ok(recommendationService.getTopRatedRecommendations());
    }

    @GetMapping("/trending")
    public ResponseEntity<List<RecommendationResponseDTO>> getTrendingRecommencations() {
        return ResponseEntity.ok(recommendationService.getTrendingRecommendations());
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<RecommendationResponseDTO>> getGenreRecommendations(@PathVariable String genre) {
        return ResponseEntity.ok(recommendationService.getGenreRecommendations(genre));
    }

    @GetMapping("/similar/{movieId}")
    public ResponseEntity<List<RecommendationResponseDTO>> getSimilarMovies(@PathVariable Long movieId) {
        return ResponseEntity.ok(recommendationService.getSimilarMovies(movieId));
    }

    @GetMapping("/watchlist")
    public ResponseEntity<List<RecommendationResponseDTO>> getWatchlistRecommendations(@RequestParam Long userId) {
        return ResponseEntity.ok(recommendationService.getWatchlistRecommendations(userId));
    }

    @GetMapping("/personalized")
    public ResponseEntity<List<RecommendationResponseDTO>> getPersonalizedRecommendations(@RequestParam Long userId) {
        return ResponseEntity.ok(recommendationService.getPersonalizedRecommendations(userId));
    }
}
