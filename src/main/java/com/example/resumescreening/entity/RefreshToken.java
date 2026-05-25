package com.example.resumescreening.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private String username;

    private LocalDateTime expiryDate;

    public RefreshToken() {
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(
            LocalDateTime expiryDate) {

        this.expiryDate = expiryDate;
    }
}