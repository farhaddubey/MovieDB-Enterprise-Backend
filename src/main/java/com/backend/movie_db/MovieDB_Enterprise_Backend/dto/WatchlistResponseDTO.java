package com.backend.movie_db.MovieDB_Enterprise_Backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WatchlistResponseDTO {

    private Long watchlistId;

    private Long movieId;

    private String movieTitle;

    private String posterUrl;

    private Double averageRating;
}
