package com.backend.movie_db.MovieDB_Enterprise_Backend.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminAnalyticsResponseDTO {

    private String metric;

    private Long value; 
}
