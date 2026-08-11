package com.backend.movie_db.MovieDB_Enterprise_Backend.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardResponseDTO {

    private Long totalUsers;

    private Long totalMovies;

    private Long totalReviews;

    private Long totalRatings;

    private Long totalWatchlists;

    private Long totalActorFollows; 
}
