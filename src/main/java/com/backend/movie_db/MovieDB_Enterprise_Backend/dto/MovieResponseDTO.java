package com.backend.movie_db.MovieDB_Enterprise_Backend.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieResponseDTO {

    private Long id;

    private String title;

    private String genre;

    private String description;

    private String language;

    private Integer duration;

    private String posterUrl;

    private LocalDate releaseDate;

    private Double averageRating;

    private Integer totalReviews;

    private Double popularityScore;

    private Long viewCount;
}
