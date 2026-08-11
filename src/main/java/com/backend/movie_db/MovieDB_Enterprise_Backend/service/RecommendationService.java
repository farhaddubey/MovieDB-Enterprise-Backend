package com.backend.movie_db.MovieDB_Enterprise_Backend.service;

import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.RecommendationResponseDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.Movie;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.Watchlist;
import com.backend.movie_db.MovieDB_Enterprise_Backend.repository.MovieRepository;
import com.backend.movie_db.MovieDB_Enterprise_Backend.repository.RatingRepository;
import com.backend.movie_db.MovieDB_Enterprise_Backend.repository.RecommendationRepository;
import com.backend.movie_db.MovieDB_Enterprise_Backend.repository.WatchlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

    private final WatchlistRepository watchlistRepository;

    private final RatingRepository ratingRepository;

    private final MovieRepository movieRepository;

    private RecommendationResponseDTO mapToResponse(Movie movie, String reason) {
        return RecommendationResponseDTO.builder()
                .movieId(movie.getId())
                .title(movie.getTitle())
                .posterUrl(movie.getPosterUrl())
                .genre(movie.getGenre())
                .averageRating(movie.getAverageRating())
                .reason(reason)
                .build();
    }

    public List<RecommendationResponseDTO> getTopRatedRecommendations() {
        return recommendationRepository
                .findTop20ByActiveTrueOrderByAverageRatingDesc()
                .stream()
                .map(movie -> mapToResponse(movie, "Top rated movie"))
                .toList();
    }

    public List<RecommendationResponseDTO> getGenreRecommendations(String genre) {
        return recommendationRepository.findTop20GenreAndActiveTrueOrderByAverageRatingDesc(genre)
                .stream()
                .map(movie -> mapToResponse(movie, "Because you like" + genre)).toList();
    }

    public List<RecommendationResponseDTO> getTrendingRecommendations() {
        return movieRepository.findTrendingMovies(
                PageRequest.of(0, 20)
        ).stream().map(movie -> mapToResponse(movie, "Trending Now")).toList();
    }

    public List<RecommendationResponseDTO> getSimilarMovies(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(
                        () -> new RuntimeException("Movie not found.")
                );
        return recommendationRepository.findTop20GenreAndActiveTrueOrderByAverageRatingDesc(movie.getGenre())
                .stream()
                .filter(
                        m -> !m.getId().equals(movieId)
                ).limit(10)
                .map(m -> mapToResponse(m, "similar to " + movie.getTitle())).toList();
    }

    public List<RecommendationResponseDTO> getWatchlistRecommendations(Long userId) {
        List<Watchlist> watchlists = watchlistRepository.findByUserIdOrderByCreatedAtDesc(userId);
        if (watchlists.isEmpty()) {
            return getTopRatedRecommendations();
        }

        String favouriteGenre = watchlists.stream().map(item -> item.getMovie().getGenre())
                .findFirst().orElse("ACTION");
        return getGenreRecommendations(favouriteGenre);
    }

    public List<RecommendationResponseDTO> getPersonalizedRecommendations(Long userId) {
        return getWatchlistRecommendations(userId); 
    }
}
