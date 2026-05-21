package com.example.resumescreening.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.resumescreening.dto.LoginRequest;
import com.example.resumescreening.dto.RegisterRequest;
import com.example.resumescreening.entity.User;
import com.example.resumescreening.repository.UserRepository;
import com.example.resumescreening.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

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
    public String login(
            @RequestBody LoginRequest request) {

        User user =
                userRepository
                        .findByUsername(
                                request.getUsername())
                        .orElse(null);

        if (user == null) {
            return "User Not Found";
        }

        if (!encoder.matches(
                request.getPassword(),
                user.getPassword())) {

            return "Invalid Password";
        }

        return JwtUtil.generateToken(
                user.getUsername());
    }
}