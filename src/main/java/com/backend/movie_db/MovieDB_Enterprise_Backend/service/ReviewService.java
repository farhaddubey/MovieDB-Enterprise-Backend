package com.backend.movie_db.MovieDB_Enterprise_Backend.service;

import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.ReviewRequestDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.ReviewResponseDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.exception.ReviewAlreadyExistsException;
import com.backend.movie_db.MovieDB_Enterprise_Backend.exception.ReviewNotFoundException;
import com.backend.movie_db.MovieDB_Enterprise_Backend.exception.ReviewOwnershipException;
import com.backend.movie_db.MovieDB_Enterprise_Backend.exception.UserNotFoundException;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.Movie;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.Review;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.ReviewLike;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.User;
import com.backend.movie_db.MovieDB_Enterprise_Backend.repository.MovieRepository;
import com.backend.movie_db.MovieDB_Enterprise_Backend.repository.ReviewLikeRepository;
import com.backend.movie_db.MovieDB_Enterprise_Backend.repository.ReviewRepository;
import com.backend.movie_db.MovieDB_Enterprise_Backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final ReviewLikeRepository reviewLikeRepository;

    private final MovieRepository movieRepository;

    private final UserRepository userRepository;

    private ReviewResponseDTO mapToResponse(Review review) {

        return ReviewResponseDTO.builder()
                .reviewId(review.getId())
                .userId(review.getUser().getId())
                .userName(review.getUser().getName())
                .movieId(review.getMovie().getId())
                .reviewText(review.getReviewText())
                .rating(review.getRating())
                .likesCount(review.getLikesCount())
                .dislikesCount(review.getDislikesCount())
                .createdAt(review.getCreatedAt())
                .helpfulScore(review.getLikesCount() - review.getDislikesCount())
                .build();
    }
    // Millions of READS and only few writes
    // That's why we'll be storing precomputed values

    public ReviewResponseDTO addReview(Long userId, Long movieId, ReviewRequestDTO reviewRequestDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new RuntimeException("User not found.") // 500
                );
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(
                        () -> new RuntimeException("Movie not found.")
                );
        reviewRepository.findByUserIdAndMovieId(userId, movieId)
                .ifPresent(review -> {
                    throw new ReviewAlreadyExistsException(userId, movieId);
                });

        Review review = Review.builder()
                .reviewText(reviewRequestDTO.getReviewText())
                .rating(reviewRequestDTO.getRating())
                .likesCount(0L)
                .dislikesCount(0L)
                .user(user)
                .movie(movie)
                .build();
        Review savedReview = reviewRepository.save(review);
        updateMovieRating(movie);
        return mapToResponse(savedReview);
    }

    public ReviewResponseDTO updateReview(Long reviewId, Long userId, ReviewRequestDTO requestDTO) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(
                        () -> new ReviewNotFoundException(reviewId)
                );
        if (!review.getUser().getId().equals(userId)) {
            throw new RuntimeException("Not review owner.");
        }

        review.setReviewText(requestDTO.getReviewText());
        review.setRating(requestDTO.getRating());

        Review updatedReview = reviewRepository.save(review);
        updateMovieRating(review.getMovie());

        return mapToResponse(updatedReview);
    }

    public void deleteReview(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(
                        () -> new ReviewNotFoundException(reviewId)
                );
        if (!review.getUser().getId().equals(userId)) {
            throw new ReviewOwnershipException();
        }
    }

    private void updateMovieRating(Movie movie) {
        List<Review> reviews = reviewRepository.findByMovieId(movie.getId());

        if (reviews.isEmpty()) {
            movie.setAverageRating(0.0);
            movie.setTotalReviews(0);
            movieRepository.save(movie);
            return;
        }

        double average = reviews.stream().mapToInt(Review::getRating).average().orElse(0);
        movie.setAverageRating(average);
        movie.setTotalReviews(reviews.size());
        movieRepository.save(movie);
    }

    public void likeReview(Long reviewId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );

        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new ReviewNotFoundException(reviewId)
        );

        ReviewLike reaction = reviewLikeRepository.findByUserAndReview(user, review)
                .orElse(null);
        if (reaction == null) {
            reaction = ReviewLike.builder()
                    .user(user)
                    .review(review)
                    .isLiked(true)
                    .build();
            reviewLikeRepository.save(reaction);
        } else {
            reaction.setIsLiked(true);
            reviewLikeRepository.save(reaction);
        }

        updateReactionCounts(review);
    }

    public void dislikeReview(Long reviewId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );

        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new ReviewNotFoundException(reviewId)
        );

        ReviewLike reaction = reviewLikeRepository.findByUserAndReview(user, review).orElse(null);

        if (reaction == null) {
            reaction = ReviewLike.builder()
                    .user(user)
                    .review(review)
                    .isLiked(false)
                    .build();
            reviewLikeRepository.save(reaction);
        } else {
            reaction.setIsLiked(false);
            reviewLikeRepository.save(reaction);
        }

        updateReactionCounts(review);
    }

    private void updateReactionCounts(Review review) {
        long likes = reviewLikeRepository.countByReviewAndIsLikedTrue(review);
        long dislikes = reviewLikeRepository.countByReviewAndIsLikedFalse(review);

        // Once we are calling the setter of the required arguments
        // their values are updated
        review.setLikesCount(likes);
        review.setDislikesCount(dislikes);

        reviewRepository.save(review);
    }

    public List<ReviewResponseDTO> getMovieReviews(Long movieId) {
        return reviewRepository.findByMovieIdOrderByCreatedAtDesc(movieId)
                .stream()
                .map(
                        this::mapToResponse
                ).toList();
    }

    // Movie -> Review -> Newest First (CreatedAtDesc)
    // Current : Newest First
    // Better : Most Helpful first
    // Score = Likes - Dislikes

}

// saving review & updating movie
// both should happen at the same time
// if one fails then another should also be the same
// So to gurantee integrity we use @Transactional
