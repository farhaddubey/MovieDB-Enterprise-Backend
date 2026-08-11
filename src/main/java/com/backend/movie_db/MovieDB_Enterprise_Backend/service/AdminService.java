package com.backend.movie_db.MovieDB_Enterprise_Backend.service;

import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.AdminDashboardResponseDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.UserManagementResponseDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.exception.UserNotFoundException;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.Rating;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.Review;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.User;
import com.backend.movie_db.MovieDB_Enterprise_Backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    private final MovieRepository movieRepository;

    private final ReviewRepository reviewRepository;

    private final RatingRepository ratingRepository;

    private final WatchlistRepository watchlistRepository;

    private final ActorFollowRepository actorFollowRepository;

    public AdminDashboardResponseDTO getDashboardStats() {
        return AdminDashboardResponseDTO
                .builder()
                .totalUsers(userRepository.count())
                .totalMovies(movieRepository.count())
                .totalReviews(reviewRepository.count())
                .totalRatings(ratingRepository.count())
                .totalWatchlists(watchlistRepository.count())
                .totalActorFollows(actorFollowRepository.count())
                .build();
    }

    private UserManagementResponseDTO mapToUserResponse(User user) {
        return UserManagementResponseDTO.builder()
                .userId(user.getId())
                .username(user.getName())
                .email(user.getEmail())
                .enabled(user.isEnabled())
                .role(user.getRole().getName())
                .build();
    }

    public List<UserManagementResponseDTO> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(this::mapToUserResponse)
                .toList();
    }

    private void banUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found.")
        );
        user.setEnabled(false);
        userRepository.save(user);
    }

    public void activeUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );
        user.setEnabled(true);
        userRepository.save(user);
    }

    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(
                        () -> new RuntimeException("Review not found.")
                );
        reviewRepository.delete(review);
    }

    public void deleteRating(Long ratingId) {
        Rating rating = ratingRepository.findById(ratingId).orElseThrow(
                () -> new RuntimeException("Rating not found.")
        );
        ratingRepository.delete(rating);
    }

    public List<Object[]> getMostFollowedActors() {
        return ActorFollowRepository.findMostFollowedActors();
    }

    public List<Object[]> getMostActiveUsers() {
        return reviewRepository.findMostActiveUsers();
    }
}
