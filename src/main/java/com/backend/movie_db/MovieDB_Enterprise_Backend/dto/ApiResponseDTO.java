package com.backend.movie_db.MovieDB_Enterprise_Backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseDTO<T> {

    private boolean success;

    private String message;

    private T data;
}
