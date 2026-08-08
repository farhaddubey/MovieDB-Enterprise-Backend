package com.backend.movie_db.MovieDB_Enterprise_Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WatchlistRequestDTO {

    private Long movieId;
}

// From the request DTOs we only extract the data we don't set any data
// Or we do not use any builder function
// @Getter @Setter