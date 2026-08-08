package com.backend.movie_db.MovieDB_Enterprise_Backend.service;

import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.MovieRequestDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.MovieResponseDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.exception.MovieNotFoundException;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.Movie;
import com.backend.movie_db.MovieDB_Enterprise_Backend.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieResponseDTO createMovie(MovieRequestDTO requestDTO) {
        Movie movie = Movie.builder()
                .title(requestDTO.getTitle())
                .description(requestDTO.getDescription())
                .genre(requestDTO.getGenre())
                .duration(requestDTO.getDuration())
                .languages(requestDTO.getLanguages())
                .posterUrl(requestDTO.getPosterUrl())
                .releaseDate(requestDTO.getReleaseDate())
                .averageRating(0.0)
                .totalReviews(0)
                .viewCount(0L)
                .popularityScore(0.0)
                .active(true)
                .build();

        return mapToResponse(movieRepository.save(movie));
    }

    public MovieResponseDTO getMovieById(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(
                        () -> new MovieNotFoundException(movieId)
                );
        // We are updating the view count for this people
        movie.setViewCount(movie.getViewCount() + 1);

        // If we don't save the value will not be updated
        movieRepository.save(movie);

        return mapToResponse(movie);
    }

    public MovieResponseDTO updateMovie(Long movieId, MovieRequestDTO requestDTO) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(
                        () -> new MovieNotFoundException(movieId)
                );
        movie.setTitle(requestDTO.getTitle());
        movie.setDescription(requestDTO.getDescription());
        movie.setGenre(requestDTO.getGenre());
        movie.setLanguages(requestDTO.getLanguages());
        movie.setDuration(requestDTO.getDuration());
        movie.setPosterUrl(requestDTO.getPosterUrl());
        movie.setReleaseDate(requestDTO.getReleaseDate());

        return mapToResponse(movieRepository.save(movie));
    }

    public void deleteMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new MovieNotFoundException(movieId)
        );

        movie.setActive(false);
        movieRepository.save(movie);
    }

    private MovieResponseDTO mapToResponse(Movie movie) {
        return MovieResponseDTO.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .description(movie.getDescription())
                .language(movie.getLanguages())
                .duration(movie.getDuration())
                .posterUrl(movie.getPosterUrl())
                .releaseDate(movie.getReleaseDate())
                .averageRating(movie.getAverageRating())
                .totalReviews(movie.getTotalReviews())
                .popularityScore(movie.getPopularityScore())
                .viewCount(movie.getViewCount())
                .build();
    }

    public List<MovieResponseDTO> getTopRatedMovies() {
        return movieRepository.findTop20ByActiveTrueOrderByAverageRatingDesc()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}
