package com.backend.movie_db.MovieDB_Enterprise_Backend.controller;

import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.AdminDashboardResponseDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.dto.UserManagementResponseDTO;
import com.backend.movie_db.MovieDB_Enterprise_Backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardResponseDTO> getDashboardStats() {
        return ResponseEntity.ok(adminService.getDashboardStats());
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserManagementResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @PutMapping("/users/{userId}/ban")
    public ResponseEntity<String> banUser(@PathVariable Long userId) {
        adminService.banUser(userId);

        return ResponseEntity.ok("User banned successfullly");
    }

    @PutMapping("/users/{userId}/activate")
    public ResponseEntity<String> activeUser(@PathVariable Long userId) {
        return ResponseEntity.ok("User activated successfully.");
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        adminService.deleteReview(reviewId);
        return ResponseEntity.ok("Review deleted successfully.");
    }

    @DeleteMapping("/ratings/{ratingId}")
    public ResponseEntity<String> deleteRating(@PathVariable Long ratingId) {
        return ResponseEntity.ok("Rating deleted successfully.");
    }

    @GetMapping("/analytics/active-users")
    public ResponseEntity<List<Object[]>> getMostActiveUsers () {
        return ResponseEntity.ok(adminService.getMostActiveUsers());
    }

    @GetMapping("/analytics/followed-actors")
    public ResponseEntity<List<Object[]>> getMostFollowedActors() {
        return ResponseEntity.ok(adminService.getMostFollowedActors());
    }

    
}
