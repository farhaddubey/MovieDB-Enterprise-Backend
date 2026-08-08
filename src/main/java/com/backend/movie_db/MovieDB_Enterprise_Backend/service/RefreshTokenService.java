package com.backend.movie_db.MovieDB_Enterprise_Backend.service;

import com.backend.movie_db.MovieDB_Enterprise_Backend.model.RefreshToken;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.User;
import com.backend.movie_db.MovieDB_Enterprise_Backend.repository.RefreshTokenRepository;
import com.backend.movie_db.MovieDB_Enterprise_Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    @Value("${jwt.refresh-expiration")
    private Long refreshTokenDuration;

    public RefreshToken createRefreshToken(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new RuntimeException("User not found")
                ); // ERROR : 500
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenDuration))
                .build();
        return refreshTokenRepository.save(refreshToken); 
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh Token Expired");
        }
        return token;
    }

    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(
                        () -> new RuntimeException("Refresh token not found")
                );
    }

    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }
}


// RESPONSIBILITIES :
// Creating Refresh Token
// Validating Refresh Token
// Deleting Refresh Token
// Rotating refresh Token
// Logging out