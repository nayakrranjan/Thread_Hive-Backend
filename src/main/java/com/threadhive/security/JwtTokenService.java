package com.threadhive.security;

import com.threadhive.services.interfaces.UserService;
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

@Component
public class JwtTokenService {
    UserService userService;

    @Value("${jwt.secret}")
    private String jwtSigningKey;

    @Value("${jwt.expiration}")
    private long jwtExpirationTime;

    public JwtTokenService(UserService userService) {
        this.userService = userService;
    }

    // Check if the token is valid.
    public boolean validateToken(String token, UserDetails userDetails) {
        final UUID userId = extractUserId(token);
        return userId.equals(userDetails.getId()) && !isTokenExpired(token);
    }

    // Check if token is expired.
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Generates Token
    public String generateToken(UUID userId) {
        // Configure token expiry
        Date now = new Date();
        Date expiryDateTime = new Date(now.getTime() + jwtExpirationTime);

        // Configure user claims (roles, permissions or any custom data)
        Map<String, Object> userClaims = new HashMap<>(); // Empty for now

        return Jwts.builder()
                .setClaims(userClaims)
                .setSubject(userId.toString())
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

    // Get UserId from token
    public UUID extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return UUID.fromString(claims.getSubject());
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
