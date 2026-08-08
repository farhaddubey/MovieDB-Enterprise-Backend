package com.backend.movie_db.MovieDB_Enterprise_Backend.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "review_likes",
uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "review_id"})
})
// Why this unique constraint
// Without it
// Farhad -> Like -> Like -> Like
// Duplicate would make entry | so user_id_review_id : unique only
// Only one reaction per user 
public class ReviewLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isLiked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;
}
