package com.mindhaven.demo.Configurations.SecurityConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {


    Logger log = LoggerFactory.getLogger(getClass());

    private static final String SECRET_KEY = "e03c24c66303d84372e3c4a057a93e70664f4501e7116d6b5ae544173fa26b5a";
    private static final long EXPIRATION_TIME = 3600000; 


    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String subject) {
        log.info("Generating JWT in JwtUtil");
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token, String email) {
        return getEmailFromToken(token).equals(email) && !isTokenExpired(token);
    }

    public String getEmailFromToken(String token) {
        log.info("Attempting to get email from token");
        return getClaimsFromToken(token).getSubject();
    }

    private Claims getClaimsFromToken(String token) {
        log.info("Attempting to get claims from token from token");
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        return getClaimsFromToken(token).getExpiration().before(new Date());
    }

    public String extractEmail(String token) {
        return getEmailFromToken(token);
    }
}
