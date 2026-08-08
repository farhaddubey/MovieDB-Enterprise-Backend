package com.backend.movie_db.MovieDB_Enterprise_Backend.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActorResponseDTO {

    private Long actorId;

    private String name;

    private String profileImage;

    private String nationality;

    private Long followCount;
}
