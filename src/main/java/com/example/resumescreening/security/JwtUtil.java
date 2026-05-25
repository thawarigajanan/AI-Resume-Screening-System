package com.example.resumescreening.security;

import java.util.Date;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class JwtUtil {

    private static final SecretKey SECRET_KEY =
            Keys.hmacShaKeyFor(
                    "mysecretkeymysecretkeymysecretkey12"
                            .getBytes());

    public static String generateToken(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis()
                                + 1000 * 60 * 60))
                .signWith(SECRET_KEY)
                .compact();
    }
    
    public static String generateRefreshToken(
            String username) {

        return Jwts.builder()

                .setSubject(username)

                .setIssuedAt(new Date())

                .setExpiration(

                        new Date(
                                System.currentTimeMillis()
                                        + 1000L * 60 * 60 * 24 * 7))

                .signWith(SECRET_KEY)

                .compact();
    }

    public static String extractUsername(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public static boolean validateToken(
            String token,
            String username) {

        String extractedUsername =
                extractUsername(token);

        return extractedUsername.equals(username);
    }
}