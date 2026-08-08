package com.backend.movie_db.MovieDB_Enterprise_Backend.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDTO {

    private Long reviewId;

    private Long userId;

    private String userName;

    private Long movieId;

    private String reviewText;

    private Integer rating;

    private Long likesCount;

    private Long dislikesCount;

    private LocalDateTime createdAt;

    private long helpfulScore; 
}
