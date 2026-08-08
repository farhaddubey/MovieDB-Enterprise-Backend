package com.backend.movie_db.MovieDB_Enterprise_Backend.controller;

import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.WatchlistRequestDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.WatchlistResponseDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.Watchlist;
import com.backend.movie_db.MovieDB_Enterprise_Backend.service.WatchlistService;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/watchlist")
@RequiredArgsConstructor
public class WatchlistController {

    private final WatchlistService watchlistService;

    @PostMapping
    public ResponseEntity<WatchlistResponseDTO> addToWatchList(@RequestParam Long userId, @RequestBody WatchlistRequestDTO watchlistRequestDTO) {
        return ResponseEntity.ok(watchlistService.addToWatchlist(userId, watchlistRequestDTO.getMovieId()));
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<String> removeFromWatchList(@RequestParam Long userId, @RequestParam Long movieId) {
        watchlistService.removeFromWatchlist(userId, movieId);
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping
    public ResponseEntity<List<WatchlistResponseDTO>> getMyWatchlist(@RequestParam Long userId) {
        return ResponseEntity.ok(watchlistService.getWatchlist(userId));
    }

    @GetMapping("/check/{movieId}")
    public ResponseEntity<Boolean> isInWatchlist(@RequestParam Long userId, @PathVariable Long movieId) {
        return ResponseEntity.ok(watchlistService.isMovieInWatchlist(userId, movieId));
    }

    @GetMapping("/count/{movieId}")
    public ResponseEntity<Long> getMovieCount(@PathVariable Long movieId) {
        return ResponseEntity.ok(watchlistService.getWatchlistCount(movieId));
    }
}
