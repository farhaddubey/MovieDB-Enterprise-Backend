package com.backend.movie_db.MovieDB_Enterprise_Backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String token;

    private Instant expiryDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
