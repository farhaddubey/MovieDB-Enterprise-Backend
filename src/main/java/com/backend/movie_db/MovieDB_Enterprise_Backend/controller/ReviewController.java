package com.backend.movie_db.MovieDB_Enterprise_Backend.controller;

import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.ReviewRequestDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.ReviewResponseDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/movies/{movieId}/reviews")
    public ResponseEntity<ReviewResponseDTO> addReview(@PathVariable Long movieId, @RequestParam Long userId, @Valid @RequestBody ReviewRequestDTO requestDTO) {
        return ResponseEntity.ok(reviewService.addReview(userId, movieId, requestDTO));
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponseDTO> updateReview(@PathVariable Long reviewId, @RequestParam Long userId, @Valid @RequestBody ReviewRequestDTO requestDTO) {
        return ResponseEntity.ok(reviewService.updateReview(reviewId, userId, requestDTO));
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId, @RequestParam Long userId) {
        reviewService.deleteReview(reviewId, userId);
        return ResponseEntity.ok("Deleted successfullly");
    }

    @PostMapping("/reviews/{reviewId}/like")
    public ResponseEntity<String> createReview(@PathVariable Long reviewId, @RequestParam Long userId) {
        reviewService.likeReview(reviewId, userId);
        return ResponseEntity.ok("Review created");
    }

    @PostMapping("/reviews/{reviewId}/dislike")
    public ResponseEntity<String> dislikeReview(Long reviewId, Long userId) {
        reviewService.dislikeReview(reviewId, userId);
        return ResponseEntity.ok("Review dislike created");
    }

    @GetMapping("/movies/{movieId}/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> fetchMovieReviews(@PathVariable Long movieId) {
        return ResponseEntity.ok(reviewService.getMovieReviews(movieId));
    }
}


// POST    /movies/{movieId}/reviews
// PUT     /reviews/{reviewId}
// DELETE  /reviews/{reviewId}
// POST    /reviews/{reviewid}/like
// POST    /reviews/{reviewId}/dislike
// GET     /movies/{movieId}/reviews