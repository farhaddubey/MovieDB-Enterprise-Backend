package com.backend.movie_db.MovieDB_Enterprise_Backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendationResponseDTO {

    private Long movieId;

    private String title;

    private String posterUrl;

    private String genre;

    private Double averageRating;

    private String reason;
}
