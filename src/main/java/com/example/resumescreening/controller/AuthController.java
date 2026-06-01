package com.example.resumescreening.controller;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.resumescreening.dto.LoginRequest;
import com.example.resumescreening.dto.RegisterRequest;
import com.example.resumescreening.entity.User;
import com.example.resumescreening.repository.UserRepository;
import com.example.resumescreening.security.JwtUtil;

import java.time.LocalDateTime;

import com.example.resumescreening.dto.AuthResponse;

import com.example.resumescreening.entity.RefreshToken;

import com.example.resumescreening.repository.RefreshTokenRepository;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RefreshTokenRepository
            refreshTokenRepository;

    private BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder();

    @PostMapping("/register")
    public String register(
            @RequestBody RegisterRequest request) {

        User user = new User();

        user.setUsername(request.getUsername());

        user.setPassword(
                encoder.encode(request.getPassword()));

        user.setRole(request.getRole());

        userRepository.save(user);

        return "User Registered Successfully";
    }

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody LoginRequest request) {

        User user =
                userRepository
                        .findByUsername(
                                request.getUsername())
                        .orElse(null);

        if (user == null) {

            throw new RuntimeException(
                    "User Not Found");
        }

        if (!encoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException(
                    "Invalid Password");
        }

        String accessToken =
                JwtUtil.generateToken(
                        user.getUsername());

        String refreshToken =
                JwtUtil.generateRefreshToken(
                        user.getUsername());

        RefreshToken tokenEntity =
                new RefreshToken();

        tokenEntity.setToken(refreshToken);

        tokenEntity.setUsername(
                user.getUsername());

        tokenEntity.setExpiryDate(
                LocalDateTime.now()
                        .plusDays(7));

        refreshTokenRepository
                .save(tokenEntity);

        return new AuthResponse(
                accessToken,
                refreshToken);
    }
    @PostMapping("/logout")

    public String logout(

            @RequestParam String refreshToken) {

        refreshTokenRepository
                .deleteByToken(refreshToken);

        return "Logged out successfully";
    }
}