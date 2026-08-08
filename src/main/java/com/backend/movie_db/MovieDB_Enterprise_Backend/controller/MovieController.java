package com.backend.movie_db.MovieDB_Enterprise_Backend.controller;

import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.MovieRequestDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.MovieResponseDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<MovieResponseDTO> createMovie(@Valid @RequestBody MovieRequestDTO movieRequestDTO) {
        return ResponseEntity.ok(movieService.createMovie(movieRequestDTO));
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<MovieResponseDTO> updateMovie(@PathVariable Long movieId, @Valid @RequestBody MovieRequestDTO requestDTO) {
        return ResponseEntity.ok(movieService.updateMovie(movieId, requestDTO));
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long movieId) {
        movieService.deleteMovie(movieId);
        return ResponseEntity.ok("Movie deleted successfully");
    }

    @GetMapping("/{movidId}")
    public ResponseEntity<MovieResponseDTO> getMovieById(@PathVariable Long movieId) {
        return ResponseEntity.ok(movieService.getMovieById(movieId));
    }

    @GetMapping
    public ResponseEntity<Page<MovieResponseDTO>> getAllMovies(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "title") String sortBy) {
        return ResponseEntity.ok(movieService.getAllMovies(page, size, sortBy));
    }

    @GetMapping("/search/title")
    public ResponseEntity<Page<MovieResponseDTO>> searchByTitle(@RequestParam String title, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(movieService.searchByTitle(title, page, size));
    }

    @GetMapping("/search/genre")
    public ResponseEntity<Page<MovieResponseDTO>> searchByGenre(@RequestParam String genre, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(movieService.searchByReleaseYear(year, page, size));
    }

    @GetMapping("/trending")
    public ResponseEntity<List<MovieResponseDTO>> trendingMovies() {
        return ResponseEntity.ok(movieService.getTrendingMovies());
    }

    @GetMapping("/popular")
    public ResponseEntity<List<MovieResponseDTO>> popularMovies() {
        return ResponseEntity.ok(movieService.getPopularMovies());
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<MovieResponseDTO>> upcomingMovies() {
        return ResponseEntity.ok(movieService.getUpcomingMovies());
    }
}

// /api/movies?page=0&size=20
// /api/movies?page=0&size=20&sortBy=viewCount
// /api/movies/search/title?title=avenger (RequestParam)
// /api/movies/search/genre?genre=Action
// /api/movies/search/year?year=2025
// /api/movies/trending
// /api/movies/popular
// /api/movies/upcoming
