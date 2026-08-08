package com.backend.movie_db.MovieDB_Enterprise_Backend.controller;

import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.RatingRequestDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.RatingResponseDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.repository.RatingRepository;
import com.backend.movie_db.MovieDB_Enterprise_Backend.security.CustomUserDetailsService;
import com.backend.movie_db.MovieDB_Enterprise_Backend.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/movies/{movieId}/ratings")
    public ResponseEntity<RatingResponseDTO> rateMovie(@PathVariable Long movieId, @RequestParam Long userId, @Valid @RequestBody RatingRequestDTO ratingRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ratingService.rateMovie(movieId, userId, ratingRequestDTO)
        );
    }

    @DeleteMapping("/movies/{movieId}/ratings")
    public ResponseEntity<String> deleteRating(@PathVariable Long movieId, @RequestParam Long userId) {
        ratingService.deleteRating(movieId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                "Deleted successfully."
        );
    }

    @GetMapping("/movies/{movieId}/ratings/me")
    public ResponseEntity<RatingResponseDTO> getMyRating(@PathVariable Long movieId, @RequestParam Long userId) {
        return ResponseEntity.ok(ratingService.getUserRating(movieId, userId));
    }

    @GetMapping("/movies/{movieId}/ratings/count")
    public ResponseEntity<Long> getRatingCount(@PathVariable Long movieId) {
        return ResponseEntity.ok(ratingService.getMovieRatingCount(movieId));
    }
}

// POST : /api/movies/{movieId}/ratings
// DELETE : /api/movies/{movieId}/ratings
// GET : /api/movies/{movieId}/ratings/me
// GET : /api/movies/{movieId}/ratings/count
