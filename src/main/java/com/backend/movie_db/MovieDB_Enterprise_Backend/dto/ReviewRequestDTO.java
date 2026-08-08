package com.backend.movie_db.MovieDB_Enterprise_Backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDTO {

    @NotBlank
    private String reviewText;

    @Min(1)
    @Max(10)
    private Integer rating;
}


