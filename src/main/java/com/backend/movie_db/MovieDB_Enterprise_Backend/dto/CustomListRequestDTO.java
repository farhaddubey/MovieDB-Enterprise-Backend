package com.backend.movie_db.MovieDB_Enterprise_Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomListRequestDTO {

    private String name;

    private String description;

    private Boolean isPublic; 
}
