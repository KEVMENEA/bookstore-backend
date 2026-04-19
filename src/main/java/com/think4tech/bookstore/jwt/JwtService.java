package com.think4tech.bookstore.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String AUTHORITIES_CLAIM = "authorities";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generate token using full UserDetails.
     * Best choice when userDetails already contains authorities.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(userDetails.getUsername(), userDetails.getAuthorities());
    }

    /**
     * Backward-compatible method: generate token with email only.
     */
    public String generateToken(String email) {
        return generateToken(email, List.of());
    }

    /**
     * Generate token with email + authorities.
     */
    public String generateToken(String email, Collection<? extends GrantedAuthority> authorities) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(
                AUTHORITIES_CLAIM,
                authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
        );

        return buildToken(claims, email);
    }

    private String buildToken(Map<String, Object> claims, String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Returns all authorities exactly as stored in token.
     * Example: ["ROLE_ADMIN"]
     */
    public List<String> extractAuthorities(String token) {
        Claims claims = extractAllClaims(token);
        Object rawAuthorities = claims.get(AUTHORITIES_CLAIM);

        if (rawAuthorities instanceof List<?> list) {
            return list.stream()
                    .filter(item -> item != null)
                    .map(Object::toString)
                    .toList();
        }

        return List.of();
    }

    /**
     * Returns roles without ROLE_ prefix.
     * Example: ["ADMIN"]
     */
    public List<String> extractRoles(String token) {
        return extractAuthorities(token).stream()
                .map(authority -> authority.startsWith("ROLE_")
                        ? authority.substring(5)
                        : authority)
                .toList();
    }

    public boolean hasRole(String token, String role) {
        String expectedRole = role.startsWith("ROLE_") ? role : "ROLE_" + role;
        return extractAuthorities(token).contains(expectedRole);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, String email) {
        return extractEmail(token).equals(email) && !isTokenExpired(token);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return extractEmail(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }
}