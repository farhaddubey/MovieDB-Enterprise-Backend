package com.backend.movie_db.MovieDB_Enterprise_Backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomListResponseDTO {

    private Long listId;

    private String name;

    private String description;

    private Boolean isPublic;

    private Long totalMovies;
}
