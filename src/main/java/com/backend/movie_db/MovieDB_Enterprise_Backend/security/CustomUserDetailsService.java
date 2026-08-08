package com.backend.movie_db.MovieDB_Enterprise_Backend.security;

import com.backend.movie_db.MovieDB_Enterprise_Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.backend.movie_db.MovieDB_Enterprise_Backend.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found.")
        );

       return new org.springframework.security.core.userdetails.User(
               user.getEmail(),
               user.getPassword(),
               user.isEnabled(),
               true,
               true,
               true,
               user.getRoles()
                       .stream()
                       .map(role -> new SimpleGrantedAuthority(role.getName()))
                       .collect(Collectors.toList())
       );

    }
}

// Spring Security talks with the repostiory db
// via CustomUserDetailsService
// Spring only know User details not User
// CustomUserDetailsService acts a adapter

// LOGIN -> AUTHENTICATION MANAGER -> CustomUserDetailsService -> DB Query -> UserDetails (Spring) -> Password Check
