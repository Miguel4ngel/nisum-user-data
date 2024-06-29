package com.nisum.user_data.domain.service.jwt;

import com.nisum.user_data.data.entity.user.User;
import com.nisum.user_data.data.repository.user.UserRepository;
import com.nisum.user_data.domain.exception.AuthenticationFailedException;
import com.nisum.user_data.presentation.dto.auth.AuthenticationRequestDto;
import com.nisum.user_data.presentation.dto.auth.AuthenticationResponseDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Miguel Angel
 * @since v1.0.0
 */

@Component
@Log4j2
public class JwtUtilService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long jwtExpirationInMs;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    private UserRepository userRepository;

    public JwtUtilService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    public AuthenticationResponseDto generateToken(AuthenticationRequestDto userCredentials) {
        Map<String, Object> claims = new HashMap<>();
        Optional<User> user = userRepository.findByEmailAndPassword(userCredentials.getEmail(), userCredentials.getPassword());
        if (user.isPresent()) {
            claims.put("role", user.get().getRole());
            String jwtToken = createToken(claims, userCredentials.getEmail());
            return AuthenticationResponseDto.builder().jwtAccessToken(jwtToken).build();
        } else {
            throw new AuthenticationFailedException("User not found.");
        }
    }

    private String createToken(Map<String, Object> claims, String subject) {
        log.info("Creating JWT Token for user with email [{}]", subject);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("JWT token validation failed", e);
            return false;
        }
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey())
                .build().parseClaimsJws(token).getBody().getSubject();
    }

    public String extractRoles(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey())
                .build().parseClaimsJws(token).getBody().get("role", String.class);
    }
}
