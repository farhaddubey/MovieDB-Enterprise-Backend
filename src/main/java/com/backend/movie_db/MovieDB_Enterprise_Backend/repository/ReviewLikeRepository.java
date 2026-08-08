package com.backend.movie_db.MovieDB_Enterprise_Backend.repository;

import com.backend.movie_db.MovieDB_Enterprise_Backend.model.Review;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.ReviewLike;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

    Optional<ReviewLike> findByUserAndReview(User user, Review review);

    long countByReviewAndIsLikedTrue(Review review);

    long countByReviewAndIsLikedFalse(Review review); 
}
