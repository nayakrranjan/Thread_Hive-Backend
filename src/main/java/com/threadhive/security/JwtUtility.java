package com.threadhive.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// Handles generation, validation, and extraction of authentication data from JWT tokens.
@Component
public class JwtUtility {

    // Secret key used for signing and verifying JWT tokens (getting from application.properties).
    @Value("${jwt.secret}")
    private String jwtSigningKey;

    // JWT token expiration time in milliseconds (getting from application.properties).
    @Value("${jwt.expiration}")
    private long jwtExpirationTime;

    // Checks if the token is valid.
    public boolean validateToken(String token, CustomUserDetails customUserDetails) {
        final String username = extractUsername(token);
        return username.equals(customUserDetails.getUsername()) && !isTokenExpired(token);
    }

    // Check if token is expired.
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        // Configure token expiry
        Date now = new Date();
        Date expiryDateTime = new Date(now.getTime() + jwtExpirationTime);

        // Configure user claims (roles, permissions or any custom data)
        Map<String, Object> userClaims = new HashMap<>();

        return Jwts.builder()
                .setClaims(userClaims)
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDateTime)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Generates a HMAC signing key using a secret string.
    // HMAC signing key is used when creating or validating JWT tokens.
    private Key getSigningKey() {
        byte[] keyBytes = jwtSigningKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public String extractClaim(String token, String claimKey) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get(claimKey, String.class);  // Get the claim by its key
    }

    // Get expiration from token
    public Date extractExpiration(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getExpiration();
    }

    // Get all claims/data from the token.
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
