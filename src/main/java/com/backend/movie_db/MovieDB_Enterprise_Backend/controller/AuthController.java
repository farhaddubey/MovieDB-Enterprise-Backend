package com.backend.movie_db.MovieDB_Enterprise_Backend.controller;

import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.AuthResponseDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.LoginRequestDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.RefreshTokenRequestDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.RegisterRequestDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestDTO requestDTO) {
        authService.register(requestDTO);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authService.login(loginRequestDTO));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refreshToken(@RequestBody RefreshTokenRequestDTO requestDTO) {
        return ResponseEntity.ok(authService.refreshToken(requestDTO));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader) {
        authService.logout(authorizationHeader);
        return ResponseEntity.status(HttpStatus.OK).body("Logout successful"); 
    }
}
