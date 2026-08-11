package com.backend.movie_db.MovieDB_Enterprise_Backend.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserManagementResponseDTO {

    private Long userId;

    private String username;

    private String email;

    private boolean enabled;

    private String role;
}
