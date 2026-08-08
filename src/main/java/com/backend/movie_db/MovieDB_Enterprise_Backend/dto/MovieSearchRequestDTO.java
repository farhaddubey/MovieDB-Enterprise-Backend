package com.backend.movie_db.MovieDB_Enterprise_Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieSearchRequestDTO {

    private String title;

    private String genre;

    private Integer releaseYear;
}
