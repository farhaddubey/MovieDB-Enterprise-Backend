package com.backend.movie_db.MovieDB_Enterprise_Backend.service;

import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.AuthResponseDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.LoginRequestDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.RefreshTokenRequestDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.RegisterRequestDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.RefreshToken;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.Role;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.User;
import com.backend.movie_db.MovieDB_Enterprise_Backend.repository.RoleRepository;
import com.backend.movie_db.MovieDB_Enterprise_Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final RefreshTokenService refreshTokenService;

    public void register(RegisterRequestDTO requestDTO) {

        if (userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Role role = roleRepository.findByName("ROLE_USER").orElseThrow(
                () -> new RuntimeException("Role not found")
        );
        User user = User.builder()
                .name(requestDTO.getName())
                .email(requestDTO.getEmail())
                .password(passwordEncoder.encode(requestDTO.getPassword()))
                .enabled(true)
                .roles(Set.of(role))
                .build();
        userRepository.save(user);
    }

    public AuthResponseDTO login(LoginRequestDTO requestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDTO.getEmail(),
                        requestDTO.getPassword()
                )
        );

        User user = userRepository.findByEmail(requestDTO.getEmail()).orElseThrow();
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of()
        );

        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = refreshTokenService.createRefreshToken(user.getId()).getToken();

        return AuthResponseDTO.builder().accessToken(accessToken).refreshToken(refreshToken)
                .tokenType("Bearer").build();
    }

    public AuthResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO) {
        RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenRequestDTO.getRefreshToken());

        refreshTokenService.verifyExpiration(refreshToken);

        User user = refreshToken.getUser();

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), List.of()
        );

        String accessToken = jwtService.generateToken(userDetails);
        return AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .build();
    }
    
    public void logout(String authorizationHeader) {
        String token = authorizationHeader.substring(7);

        String email = jwtService.extractUsername(token);
        User user = userRepository.findByEmail(email)
                    .orElseThrow(
                        () -> new RuntimeException("User not found")
                );
        refreshTokenService.deleteByUser(user);
    }
}
// AUTH CONTROLLER -> AUTH SERVICE -> AUTHENTICATION MANAGER -> CUSTOM USER DETAILS SERVICE -> USER REPOSITORY -> DATABASE


// AuthService.java
// Responsibilites :
// Register
// Login
// Refresh Token
// Logout


// REGISTER FLOW
// Register -> Email Exists ? -> No -> Encoding Password -> Assigning ROLE_USER -> Saving User

// LOGIN FLOW
// Password -> Authentication Manager -> JWT -> Refresh Token

// Authentication Manager :
// Because password verification should NOT happen manually
// We'll be always using Authentication Manager

// PasswordEncode : Password -> Bcrypt -> Hash