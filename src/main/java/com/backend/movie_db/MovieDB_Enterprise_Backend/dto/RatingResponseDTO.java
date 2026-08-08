package com.backend.movie_db.MovieDB_Enterprise_Backend.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponseDTO {

    private Long ratingId;

    private Long userId;

    private Long movieId;

    private Integer rating; 
}
