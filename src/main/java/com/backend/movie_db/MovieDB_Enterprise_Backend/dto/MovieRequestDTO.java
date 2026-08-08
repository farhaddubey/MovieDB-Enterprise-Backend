package com.backend.movie_db.MovieDB_Enterprise_Backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MovieRequestDTO {

    @NotBlank
    private String title;

    private String description;

    private String genre;

    private String languages;

    private Integer duration;

    private String posterUrl;

    private LocalDate releaseDate;
}
