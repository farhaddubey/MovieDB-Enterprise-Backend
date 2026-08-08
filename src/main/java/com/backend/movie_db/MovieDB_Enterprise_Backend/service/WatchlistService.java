package com.backend.movie_db.MovieDB_Enterprise_Backend.service;

import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.WatchlistResponseDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.exception.UserNotFoundException;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.Movie;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.User;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.Watchlist;
import com.backend.movie_db.MovieDB_Enterprise_Backend.repository.MovieRepository;
import com.backend.movie_db.MovieDB_Enterprise_Backend.repository.UserRepository;
import com.backend.movie_db.MovieDB_Enterprise_Backend.repository.WatchlistRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WatchlistService {

    private final WatchlistRepository watchlistRepository;

    private final UserRepository userRepository;

    private final MovieRepository movieRepository;

    private WatchlistResponseDTO mapToResponse(Watchlist watchlist) {
        Movie movie = watchlist.getMovie();

        return WatchlistResponseDTO.builder()
                .watchlistId(watchlist.getId())
                .movieId(movie.getId())
                .movieTitle(movie.getTitle())
                .posterUrl(movie.getPosterUrl())
                .averageRating(movie.getAverageRating())
                .build();
    }

    public WatchlistResponseDTO addToWatchlist(Long userId, Long movieId) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new UserNotFoundException(userId)
                );

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(
                        () -> new RuntimeException("Movie not found.")
                );

        Watchlist existing = watchlistRepository.findByUserIdAndMovieId(userId, movieId)
                .orElse(null);

        if (existing != null) {
            return mapToResponse(existing);
        }

        Watchlist watchlist = Watchlist.builder()
                .user(user)
                .movie(movie)
                .build();

        // Not the watch list is being saved that means the data will be udpated in the db
        Watchlist saved = watchlistRepository.save(watchlist);

        return mapToResponse(saved);
    }

    public void removeFromWatchlist(Long userId, Long movieId) {
        Watchlist watchlist = watchlistRepository.findByUserIdAndMovieId(userId, movieId)
                .orElseThrow(
                        () -> new RuntimeException("Movie not found in watch list.")
                );
        watchlistRepository.delete(watchlist);
    }

    public List<WatchlistResponseDTO> getWatchlist(Long userId) {
        return watchlistRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream().map(this::mapToResponse).toList();
    }

    public boolean isMovieInWatchlist(Long userId, Long movieId) {
        return watchlistRepository.existsByUserAndMovieId(userId, movieId);
    }

    public long getWatchlistCount(Long movieId) {
        return watchlistRepository.countByMovieId(movieId);
    }


}
