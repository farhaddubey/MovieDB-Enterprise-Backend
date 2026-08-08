package com.backend.movie_db.MovieDB_Enterprise_Backend.service;

import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.RatingRequestDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.RatingResponseDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.exception.MovieNotFoundException;
import com.backend.movie_db.MovieDB_Enterprise_Backend.exception.UserNotFoundException;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.Movie;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.Rating;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.User;
import com.backend.movie_db.MovieDB_Enterprise_Backend.repository.MovieRepository;
import com.backend.movie_db.MovieDB_Enterprise_Backend.repository.RatingRepository;
import com.backend.movie_db.MovieDB_Enterprise_Backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RatingService {

    private final RatingRepository ratingRepository;

    private final UserRepository userRepository;

    private final MovieRepository movieRepository;

    private RatingResponseDTO mapToResponse(Rating rating) {
        return RatingResponseDTO
                .builder()
                .ratingId(rating.getId())
                .userId(rating.getUser().getId())
                .movieId(rating.getMovie().getId())
                .rating(rating.getRating())
                .build();
    }

    public RatingResponseDTO rateMovie(Long userId, Long movieId, RatingRequestDTO requestDTO) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );

        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new MovieNotFoundException(movieId)
        );

        Rating rating = ratingRepository.findByUserIdAndMovieId(userId, movieId)
                .orElse(
                        Rating.builder().user(user).movie(movie).build()
                );
        rating.setRating(requestDTO.getRating());

        Rating savedRating = ratingRepository.save(rating);

        updateMovieAverageRating(movie);

        return mapToResponse(savedRating);
    }

    public void deleteRating(Long userId, Long movieId) {
        Rating rating = ratingRepository.findByUserIdAndMovieId(userId, movieId)
                .orElseThrow(
                        () -> new RuntimeException("Rating not found.")
                );
        Movie movie = rating.getMovie();
        ratingRepository.delete(rating);
        updateMovieAverageRating(movie);
    }

    public RatingResponseDTO getUserRating(Long userId, Long movieId) {

        Rating rating = ratingRepository.findByUserIdAndMovieId(userId, movieId)
                .orElseThrow(
                        () -> new RuntimeException("Ratinng not found.")
                );

        return mapToResponse(rating);
    }

    public void updateMovieAverageRating(Movie movie) {
        List<Rating> ratings =
                ratingRepository.findByMovieId(movie.getId());
        if (ratings.isEmpty()) {
            movie.setAverageRating(0.0);
            movieRepository.save(movie);
            return;
        }

        double average = ratings.stream()
                .mapToInt(Rating::getRating).average().orElse(0);
        movie.setAverageRating(average);
        movie.setTotalRatings((long) ratings.size());
        // Now the value will be udpated to the ssytem
        movieRepository.save(movie);
    }

    public long getMovieRatingCount(Long movieId) {
        return ratingRepository.countByMovieId(movieId);
    }
}
