package com.example.resumescreening.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY =
            "mySuperSecureJwtSecretKeyForSpringBootProject123";

    public static String generateToken(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis()
                                + 1000 * 60 * 60))
                .signWith(
                        SignatureAlgorithm.HS256,
                        SECRET_KEY)
                .compact();
    }

    public static String generateRefreshToken(
            String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis()
                                + 1000L * 60 * 60 * 24 * 7))
                .signWith(
                        SignatureAlgorithm.HS256,
                        SECRET_KEY)
                .compact();
    }

    public static Claims extractClaims(
            String token) {

        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public static String extractUsername(
            String token) {

        return extractClaims(token)
                .getSubject();
    }
}